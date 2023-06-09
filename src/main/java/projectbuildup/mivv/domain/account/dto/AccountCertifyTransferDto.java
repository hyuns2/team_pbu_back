package projectbuildup.mivv.domain.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AccountCertifyTransferDto {
    String organizationCode;
    String accountNumbers;
    String verificationCode;
}
