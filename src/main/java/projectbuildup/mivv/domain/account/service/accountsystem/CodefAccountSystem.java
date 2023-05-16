package projectbuildup.mivv.domain.account.service.accountsystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.account.dto.AccountCertifyTransferDto;
import projectbuildup.mivv.domain.account.dto.AccountRegisterDto;
import projectbuildup.mivv.domain.account.entity.Account;
import projectbuildup.mivv.domain.account.entity.BankType;
import projectbuildup.mivv.domain.account.entity.OpenBanking;
import projectbuildup.mivv.domain.account.entity.OwnAccounts;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.error.exception.CIllegalArgumentException;
import projectbuildup.mivv.global.error.exception.CInternalServerException;
import projectbuildup.mivv.global.error.exception.CResourceNotFoundException;

import java.util.HashMap;
import java.util.List;

@Primary
@Component
@Slf4j
@RequiredArgsConstructor
public class CodefAccountSystem implements AccountSystem {

    private final CodefClient codefClient;

    @Override
    public Account createAccount(AccountRegisterDto accountDto, User user) {
        String connectedId = issueConnectedId(accountDto, user);
        OwnAccounts ownAccounts = findAllAccountNumbers(connectedId, accountDto.getOrganizationCode());
        if (!ownAccounts.contains(accountDto.getAccountNumbers())) {
            throw new CResourceNotFoundException();
        }
        return new Account(accountDto.getAccountNumbers(), BankType.findByCode(accountDto.getOrganizationCode()), OpenBanking.CODEF, connectedId);
    }

    private String issueConnectedId(AccountRegisterDto accountDto, User user) {
        return codefClient.createConnectedId(accountDto);
    }

    private OwnAccounts findAllAccountNumbers(String connectedId, String bankType) {
        String result = codefClient.getOwnAccounts(bankType, connectedId);
        log.info(result);
        try {
            HashMap<String, Object> responseMap = new ObjectMapper().readValue(result, HashMap.class);
            HashMap<String, Object> dataMap = (HashMap<String, Object>) responseMap.get("data");
            List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) dataMap.get("resDepositTrust");
            List<String> accounts = list.stream()
                    .map(elem -> (String) elem.get("resAccount"))
                    .toList();
            return new OwnAccounts(accounts);
        } catch (JsonProcessingException e) {
            throw new CInternalServerException();
        }
    }

    @Override
    public String certifyTransfer(AccountCertifyTransferDto requestDto) {
        String result = codefClient.certifyTransfer(requestDto.getOrganizationCode(), requestDto.getAccountNumbers());
        try {
            HashMap<String, Object> responseMap = new ObjectMapper().readValue(result, HashMap.class);
            if (!doesSucceed((HashMap<String, Object>) responseMap.get("result"))){
                throw new CIllegalArgumentException("인증 과정에서 오류가 발생했습니다.");
            }
            HashMap<String, Object> dataMap = (HashMap<String, Object>) responseMap.get("data");
            return (String) dataMap.get("authCode");
        } catch (JsonProcessingException e) {
            throw new CInternalServerException();
        }
    }

    private boolean doesSucceed(HashMap<String, Object> resultMap){
        String code = (String) resultMap.get("code");
        return code.equals("CF-00000");
    }
}
