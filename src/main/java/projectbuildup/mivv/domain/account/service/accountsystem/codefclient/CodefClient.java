package projectbuildup.mivv.domain.account.service.accountsystem.codefclient;

import projectbuildup.mivv.domain.account.dto.AccountRegisterDto;

import java.time.LocalDate;
import java.util.Map;

public interface CodefClient {
    String issueToken();

    Map<String, Object> createConnectedId(AccountRegisterDto accountDto);

    Map<String, Object> getOwnAccounts(String bankType, String connectedId);

    Map<String, Object> getTransactionList(String connectedId, String bankCode, String accountNumbers, LocalDate startDate);

    Map<String, Object> certifyTransfer(String organizationCode, String accountNumbers);
}
