package projectbuildup.mivv.domain.account.service.accountdetails;

import projectbuildup.mivv.domain.account.entity.TransactionDetail;
import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AccountDetailsSystem {
    List<TransactionDetail> getDepositHistory(User user, LocalDate startDate);

    List<TransactionDetail> getWithdrawHistory(User user, LocalDate startDate);
}
