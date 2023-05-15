package projectbuildup.mivv.domain.account.service.accountdetails;

import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AccountDetailsSystem {
    List<Map<String, String>> getDepositHistory(User user, LocalDate startDate);

    List<Map<String, String>> getWithdrawHistory(User user, LocalDate startDate);

    String getInAmountField();

    String getOutAmountField();

    String getDateField();

    String getTimeField();
}
