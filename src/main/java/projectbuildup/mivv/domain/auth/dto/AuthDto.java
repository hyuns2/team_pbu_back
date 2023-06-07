package projectbuildup.mivv.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import projectbuildup.mivv.global.constant.ExampleValue;

public class AuthDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignupRequest{
        @NotBlank
        @Schema(description = "본인인증 코드")
        String verificationCode;
        @Email
        @Schema(description = "이메일", example = ExampleValue.User.EMAIL)
        String email;
        @Length(min = 2, max = 20)
        @Schema(description = "닉네임", example = ExampleValue.User.NICKNAME)
        String nickname;
        @Schema(description = "비밀번호", example = ExampleValue.User.PASSWORD)
        @Pattern(regexp = "\\d{6}")
        String password;
        @NotNull
        @AssertTrue
        Boolean agreement;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        @NotBlank
        @Schema(description = "본인인증 코드", example = ExampleValue.User.VERIFICATION_CODE)
        String verificationCode;
        @Schema(description = "비밀번호", example = ExampleValue.User.PASSWORD)
        String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CertifyRequest {
        @NotBlank
        @Schema(description = "본인인증 API 호출 키")
        String txId;
        @NotBlank
        @Schema(description = "콜백 URL")
        String authUrl;
        @NotBlank
        @Schema(description = "복호화 키")
        String token;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class UnlinkRequestDto {
        Long userId;
        String accessToken;
        String refreshToken;
    }

    @Getter
    @NoArgsConstructor
    public static class ReissueRequest {
        @Schema(description = "액세스 토큰", example = ExampleValue.JWT.ACCESS)
        @NotBlank
        String accessToken;

        @Schema(description = "리프레시 토큰", example = ExampleValue.JWT.REFRESH)
        @NotBlank
        String refreshToken;
    }
}
