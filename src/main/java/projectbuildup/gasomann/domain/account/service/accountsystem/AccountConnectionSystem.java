package projectbuildup.gasomann.domain.account.service.accountsystem;

import projectbuildup.gasomann.domain.account.dto.AccountRegisterDto;
import projectbuildup.gasomann.domain.account.entity.Account;
import projectbuildup.gasomann.domain.user.entity.User;

public interface AccountConnectionSystem {
    Account createAccount(AccountRegisterDto accountDto, User user);
    String certifyTransfer(String organizationCode, String accountNumbers);
    String getAccountOwner(String organizationCode, String accountNumbers, String verificationCode);

    void unlinkAccount(User user);
}
