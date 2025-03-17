package projectbuildup.gasomann.domain.account.dto;

import lombok.Getter;
import projectbuildup.gasomann.domain.account.entity.Account;

@Getter
public class AccountResponseDto {
    String accountNumbers;
    String bankType;

    public AccountResponseDto(Account account){
        this.accountNumbers = account.getAccountNumbers();
        this.bankType = account.getBankType().getDescription();
    }
}
