package projectbuildup.mivv.domain.account.service.accountsystem.codefclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.codef.api.EasyCodef;
import io.codef.api.EasyCodefServiceType;
import io.codef.api.EasyCodefUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.account.dto.AccountRegisterDto;
import projectbuildup.mivv.global.error.exception.CInternalServerException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CodefSandBoxClient implements CodefClient {
    @Value("${codef.sand-client-id}")
    String CLIENT_ID;
    @Value("${codef.sand-client-secret}")
    String CLIENT_SECRET;
    @Value("${codef.public-key}")
    String PUBLIC_KEY;
    EasyCodef codef;


    @PostConstruct
    public void init() {
        codef = new EasyCodef();
        codef.setClientInfo(CLIENT_ID, CLIENT_SECRET);
        codef.setPublicKey(PUBLIC_KEY);
    }

    @Override
    public String issueToken() {
        try {
            EasyCodef codef = new EasyCodef();
            codef.setClientInfo(CLIENT_ID, CLIENT_SECRET);
            codef.setPublicKey(PUBLIC_KEY);
            return codef.requestToken(EasyCodefServiceType.SANDBOX);
        } catch (IOException e) {
            throw new CInternalServerException();
        }
    }

    private void fillMapParameter(HashMap<String, Object> accountMap, AccountRegisterDto accountDto) {
        final String DAEGU_BANK = "0031";
        String id = accountDto.getBankId();
        String password = accountDto.getBankPassword();
        try {
            String encodedPassword = EasyCodefUtil.encryptRSA(password, codef.getPublicKey());
            accountMap.put("countryCode", "KR");
            accountMap.put("businessType", "BK");
            accountMap.put("organization", accountDto.getOrganizationCode());
            accountMap.put("clientType", "P");
            accountMap.put("loginType", "1");
            accountMap.put("id", id);
            accountMap.put("password", encodedPassword);
            if (accountDto.getOrganizationCode().equals(DAEGU_BANK)) {
                accountMap.put("withdrawAccountNo", accountDto.getAccountNumbers());
                accountMap.put("withdrawAccountPassword", accountDto.getAccountPassword());
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            log.error("암호화 중 오류가 발생했습니다.");
            throw new CInternalServerException();
        }
    }

    /**
     * 커넥티드 아이디를 발급합니다.
     *
     * @param accountDto 입력 파라미터
     * @return 응답값의 data 필드
     */
    @Override
    public Map<String, Object> createConnectedId(AccountRegisterDto accountDto) {
        List<HashMap<String, Object>> accountList = new ArrayList<>();
        HashMap<String, Object> accountMap = new HashMap<>();
        HashMap<String, Object> parameterMap = new HashMap<>();
        try {
            fillMapParameter(accountMap, accountDto);
            accountList.add(accountMap);
            parameterMap.put("accountList", accountList);
            String result = codef.createAccount(EasyCodefServiceType.SANDBOX, parameterMap);
            return getDataField(result);
        } catch (UnsupportedEncodingException | JsonProcessingException | InterruptedException e) {
            log.error("통신 중 오류가 발생했습니다.");
            throw new CInternalServerException();
        }
    }

    /**
     * 해당 사용자의 보유 계좌 목록을 반환합니다.
     *
     * @param bankType    은행 코드
     * @param connectedId 커넥티드 아이디
     * @return 보유 계좌 목록 (코드에프 테스트 계정의 경우 accountList = [06170204000000, 23850204000000, 54780300000000])
     */
    @Override
    public Map<String, Object> getOwnAccounts(String bankType, String connectedId) {
        String CODEF_OWN_ACCOUNT_API = "/v1/kr/bank/p/account/account-list";
        HashMap<String, Object> parameterMap = new HashMap<>();
        try {
            parameterMap.put("organization", bankType);
            parameterMap.put("connectedId", connectedId);
            String result = codef.requestProduct(CODEF_OWN_ACCOUNT_API, EasyCodefServiceType.SANDBOX, parameterMap);
            return getDataField(result);
        } catch (UnsupportedEncodingException | JsonProcessingException | InterruptedException e) {
            log.error("통신 중 오류가 발생했습니다.");
            throw new CInternalServerException();
        }
    }

    /**
     * 계좌의 입금 내역을 조회합니다.
     * 최신순으로 정렬됩니다.
     *
     * @param connectedId    커넥티드 아이디
     * @param bankCode       은행 코드
     * @param accountNumbers 계좌 번호
     * @param startDate      조회 시작 날짜
     * @return 응답값의 data 필드
     */
    @Override
    public Map<String, Object> getTransactionList(String connectedId, String bankCode, String accountNumbers, LocalDate startDate) {
        String CODEF_TRANSACTION_LIST_API = "/v1/kr/bank/p/account/transaction-list";
        LocalDate endDate = LocalDate.now();
        String startDateStr = startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String endDateStr = endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        HashMap<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("organization", bankCode);
        parameterMap.put("connectedId", connectedId);
        parameterMap.put("account", accountNumbers);
        parameterMap.put("startDate", startDateStr);
        parameterMap.put("endDate", endDateStr);
        parameterMap.put("orderBy", "0");
        try {
            String result = codef.requestProduct(CODEF_TRANSACTION_LIST_API, EasyCodefServiceType.SANDBOX, parameterMap);
            return getDataField(result);
        } catch (UnsupportedEncodingException | JsonProcessingException | InterruptedException e) {
            log.error("통신 중 오류가 발생했습니다.");
            throw new CInternalServerException();
        }
    }

    /**
     * 1원 인증을 수행합니다.
     * data 필드로 사용자에게 전송된 인증코드가 전달됩니다.
     *
     * @param organizationCode 은행코드
     * @param accountNumbers   계좌번호
     * @return 응답값의 data 필드
     */
    @Override
    public Map<String, Object> certifyTransfer(String organizationCode, String accountNumbers) {
        String CODEF_TRANSFER_AUTHENTICATION_API = "/v1/kr/bank/a/account/transfer-authentication";
        HashMap<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("organization", organizationCode);
        parameterMap.put("account", accountNumbers);
        parameterMap.put("inPrintType", "1");
        try {
            String result = codef.requestProduct(CODEF_TRANSFER_AUTHENTICATION_API, EasyCodefServiceType.SANDBOX, parameterMap);
            return getDataField(result);
        } catch (UnsupportedEncodingException | JsonProcessingException | InterruptedException e) {
            log.error("통신 중 오류가 발생했습니다.");
            throw new CInternalServerException();
        }
    }

    /**
     * 1원 인증을 수행합니다.
     * data 필드로 사용자에게 전송된 인증코드가 전달됩니다.
     *
     * @param organizationCode 은행코드
     * @param accountNumbers   계좌번호
     * @return 응답값의 data 필드
     */
    @Override
    public Map<String, Object> holderAuthentication(String organizationCode, String accountNumbers, String birthDate) {
        String CODEF_HOLDER_AUTHENTICATION_API = "/v1/kr/bank/a/account/holder-authentication";
        HashMap<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("organization", organizationCode);
        parameterMap.put("account", accountNumbers);
        parameterMap.put("identity", birthDate);
        try {
            String result = codef.requestProduct(CODEF_HOLDER_AUTHENTICATION_API, EasyCodefServiceType.SANDBOX, parameterMap);
            return getDataField(result);
        } catch (UnsupportedEncodingException | JsonProcessingException | InterruptedException e) {
            log.error("통신 중 오류가 발생했습니다.");
            throw new CInternalServerException();
        }
    }

    @Override
    public Map<String, Object> unlinkAccount(String connectedId, String code) {
        return null;
    }
}
