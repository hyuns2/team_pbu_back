package projectbuildup.mivv.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;

@Getter
@AllArgsConstructor
public class VerificationResponseDto {
    String code;
    boolean isNewUser;

}
