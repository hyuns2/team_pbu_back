package projectbuildup.mivv.domain.account.service.accountsystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.codef.api.EasyCodef;
import io.codef.api.EasyCodefServiceType;
import io.codef.api.EasyCodefUtil;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import projectbuildup.mivv.domain.account.dto.AccountRegisterDto;
import projectbuildup.mivv.domain.account.dto.IdPasswordBasedRegisterDto;
import projectbuildup.mivv.domain.account.entity.Account;
import projectbuildup.mivv.domain.account.entity.BankType;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;
import projectbuildup.mivv.domain.user.entity.User;
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
public class CodefClient {
    @Value("${codef.client-id}")
    String CLIENT_ID;
    @Value("${codef.client-secret}")
    String CLIENT_SECRET;
    @Value("${codef.public-key}")
    String PUBLIC_KEY;
    EasyCodef codef;
    private final String CODEF_OWN_ACCOUNT_API = "/v1/kr/bank/p/account/account-list";

    @PostConstruct
    public void init() {
        codef = new EasyCodef();
        codef.setClientInfo(CLIENT_ID, CLIENT_SECRET);
        codef.setPublicKey(PUBLIC_KEY);
    }

    public String issueToken() {
        try {
            EasyCodef codef = new EasyCodef();
            codef.setClientInfo(CLIENT_ID, CLIENT_SECRET);
            codef.setPublicKey(PUBLIC_KEY);
            return codef.requestToken(EasyCodefServiceType.API);
        } catch (IOException e) {
            throw new CInternalServerException();
        }
    }

    private void temp(HashMap<String, Object> accountMap, AccountRegisterDto accountDto) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        String id = ((IdPasswordBasedRegisterDto) accountDto).getBankId();
        String password = ((IdPasswordBasedRegisterDto) accountDto).getBankPassword();
        String encodedPassword = EasyCodefUtil.encryptRSA(password, codef.getPublicKey());
        accountMap.put("countryCode", "KR");
        accountMap.put("businessType", "BK");
        accountMap.put("organization", accountDto.getBankType());
        accountMap.put("clientType", "P");
        accountMap.put("loginType", "1");
        accountMap.put("id", id);
        accountMap.put("password", encodedPassword);
    }

    private void temp2(HashMap<String, Object> accountMap, AccountRegisterDto accountDto) {

    }

    private void fillMapParameter(HashMap<String, Object> accountMap, AccountRegisterDto accountDto) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        if (accountDto instanceof IdPasswordBasedRegisterDto) {
            temp(accountMap, accountDto);
        }
        temp(accountMap, accountDto);
    }

    public String createConnectedId(AccountRegisterDto accountDto, User user) {
        List<HashMap<String, Object>> accountList = new ArrayList<>();
        HashMap<String, Object> accountMap = new HashMap<>();
        try {
            fillMapParameter(accountMap, accountDto);
            accountList.add(accountMap);
            HashMap<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("accountList", accountList);
            String result = codef.createAccount(EasyCodefServiceType.SANDBOX, parameterMap);
            HashMap<String, Object> responseMap = new ObjectMapper().readValue(result, HashMap.class);
            HashMap<String, Object> resultMap = (HashMap<String, Object>) responseMap.get("data");
            return (String) resultMap.get("connectedId");
        } catch (Exception e) {
            e.printStackTrace();
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
    public String getOwnAccounts(BankType bankType, String connectedId) {
        HashMap<String, Object> parameterMap = new HashMap<>();
        try {
            parameterMap.put("organization", bankType.getCode());
            parameterMap.put("connectedId", connectedId);
            return codef.requestProduct(CODEF_OWN_ACCOUNT_API, EasyCodefServiceType.SANDBOX, parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CInternalServerException();
        }
    }

    /**
     * 계좌의 입금 내역을 조회합니다.
     * 최신순으로 정렬됩니다.
     *
     * @param connectedId 커넥티드 아이디
     * @param bankCode    은행 코드
     * @param accountNumbers    계좌 번호
     * @param startDate   조회 시작 날짜
     * @return (거래 일자, 거래시간, 거래 금액) 리스트
     */
    public Map<String, Object> getHistory(String connectedId, String bankCode, String accountNumbers, LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(1);
        String startDateStr = startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String endDateStr = endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        log.info(startDateStr);
        log.info(endDateStr);
        HashMap<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("organization", bankCode);
        parameterMap.put("connectedId", connectedId);
        parameterMap.put("account", accountNumbers);
        parameterMap.put("startDate", startDateStr);
        parameterMap.put("endDate", endDateStr);
        parameterMap.put("orderBy", "0");
        String productUrl = "/v1/kr/bank/p/account/transaction-list";
        try {
            String result = codef.requestProduct(productUrl, EasyCodefServiceType.SANDBOX, parameterMap);
            return new ObjectMapper().readValue(result, HashMap.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
