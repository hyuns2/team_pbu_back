package projectbuildup.mivv.domain.account.service.accountsystem;

import projectbuildup.mivv.domain.account.dto.AccountCertifyTransferDto;
import projectbuildup.mivv.domain.account.dto.AccountRegisterDto;
import projectbuildup.mivv.domain.account.entity.Account;
import projectbuildup.mivv.domain.user.entity.User;

public interface AccountSystem {
    Account createAccount(AccountRegisterDto accountDto, User user);
    String certifyTransfer(AccountCertifyTransferDto requestDto);
}
