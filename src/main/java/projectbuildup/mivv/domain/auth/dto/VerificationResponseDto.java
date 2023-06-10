package projectbuildup.mivv.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import projectbuildup.mivv.domain.account.entity.UserState;

@Getter
@AllArgsConstructor
public class VerificationResponseDto {
    String verificationCode;
    String userState;
    boolean success;

    public static VerificationResponseDto newFailResponse(){
        return new VerificationResponseDto(null, null, false);
    }
}
