package projectbuildup.mivv.domain.account.dto;

import lombok.Getter;
import projectbuildup.mivv.domain.account.entity.Account;
import projectbuildup.mivv.domain.account.entity.BankType;

@Getter
public class AccountResponseDto {
    String accountNumbers;
    String bankType;

    public AccountResponseDto(Account account){
        this.accountNumbers = account.getAccountNumbers();
        this.bankType = account.getBankType().getDescription();
    }
}
