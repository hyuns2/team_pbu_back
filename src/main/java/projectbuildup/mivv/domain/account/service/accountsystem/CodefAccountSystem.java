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
import java.util.Map;

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
        Map<String, Object> dataMap = codefClient.createConnectedId(accountDto);
        return (String) dataMap.get("connectedId");
    }

    private OwnAccounts findAllAccountNumbers(String connectedId, String bankType) {
        Map<String, Object> dataMap = codefClient.getOwnAccounts(bankType, connectedId);
        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) dataMap.get("resDepositTrust");
        List<String> accounts = list.stream()
                .map(elem -> (String) elem.get("resAccount"))
                .toList();
        return new OwnAccounts(accounts);
    }

    @Override
    public String certifyTransfer(AccountCertifyTransferDto requestDto) {
        Map<String, Object> dataMap = codefClient.certifyTransfer(requestDto.getOrganizationCode(), requestDto.getAccountNumbers());
        return (String) dataMap.get("authCode");
    }
}
