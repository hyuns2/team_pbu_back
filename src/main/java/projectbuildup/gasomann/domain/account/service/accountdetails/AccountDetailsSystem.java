package projectbuildup.gasomann.domain.account.service.accountdetails;

import projectbuildup.gasomann.domain.account.entity.TransactionDetail;
import projectbuildup.gasomann.domain.user.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface AccountDetailsSystem {
    List<TransactionDetail> getDepositHistory(User user, LocalDate startDate, LocalDate endDate);

    List<TransactionDetail> getWithdrawHistory(User user, LocalDate startDate, LocalDate endDate);
}
