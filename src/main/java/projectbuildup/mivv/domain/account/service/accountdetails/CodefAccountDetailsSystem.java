package projectbuildup.mivv.domain.account.service.accountdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.account.entity.Account;
import projectbuildup.mivv.domain.account.service.accountsystem.CodefClient;
import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CodefAccountDetailsSystem implements AccountDetailsSystem {
    private final CodefClient codefClient;
    @Override
    public List<HashMap<String, String>> getHistory(User user) {
        Account account = user.getAccount();
        String connectedId = "";
        String bankCode = account.getBankType().getCode();
        String accountNumbers = account.getAccountNumbers();
        return codefClient.getHistory(connectedId, bankCode, accountNumbers, LocalDate.now());
    }
}
