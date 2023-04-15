package projectbuildup.mivv.domain.account.service.accountdetails;

import projectbuildup.mivv.domain.user.entity.User;

import java.util.List;
import java.util.Map;

public interface AccountDetailsSystem {
    List<Map<String, String>> getDepositHistory(User user);
    List<Map<String, String>> getWithdrawHistory(User user);
    String getInAmountField();
    String getOutAmountField();
    String getDateField();
    String getTimeField();
}
