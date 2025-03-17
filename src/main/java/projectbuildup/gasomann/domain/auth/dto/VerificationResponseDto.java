package projectbuildup.gasomann.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
