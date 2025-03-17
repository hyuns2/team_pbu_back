package projectbuildup.gasomann.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class PasswordDto {
    @Getter
    public static class SendRequest{
        @NotBlank
        String verificationCode;
    }
    @Getter
    @Setter
    public static class ChangeRequest{
        @NotBlank
        String verificationCode;
        @NotBlank
        String password;
    }
    @Getter
    @AllArgsConstructor
    public static class Response{
        String code;
        String email;
    }
}
