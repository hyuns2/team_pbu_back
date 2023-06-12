package projectbuildup.mivv.domain.account.service.accountsystem;

import projectbuildup.mivv.domain.account.dto.AccountRegisterDto;
import projectbuildup.mivv.domain.account.entity.Account;
import projectbuildup.mivv.domain.user.entity.User;

public interface AccountConnectionSystem {
    Account createAccount(AccountRegisterDto accountDto, User user);
    String certifyTransfer(String organizationCode, String accountNumbers);
    String getAccountOwner(String organizationCode, String accountNumbers, String verificationCode);

    void unlinkAccount(User user);
}
