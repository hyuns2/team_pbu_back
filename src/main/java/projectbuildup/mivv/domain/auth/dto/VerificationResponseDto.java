package projectbuildup.mivv.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerificationResponseDto {
    String verificationCode;
    boolean isNewUser;
}
