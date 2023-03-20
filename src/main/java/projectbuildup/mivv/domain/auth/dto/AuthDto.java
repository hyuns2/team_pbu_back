package projectbuildup.mivv.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import projectbuildup.mivv.domain.member.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;

import java.util.Collections;

public class AuthDto {

    @Getter
    @NoArgsConstructor
    public static class SignupRequest{
        @NotBlank
        @Schema(description = "본인인증 코드", example = "random_string")
        String verificationCode;
        @Email
        @Schema(description = "이메일", example = ExampleValue.User.EMAIL)
        String email;
        @Length(min = 2, max = 20)
        @Schema(description = "닉네임", example = ExampleValue.User.NICKNAME)
        String nickname;
        @Schema(description = "비밀번호", example = ExampleValue.User.PASSWORD)
        String password;
        @NotNull
        Boolean agreement;

        /**
         * DTO 내용을 바탕으로 User 엔티티 생성
         *
         * @return 생성된 User 엔티티
         */
        public User toEntity(String encodedPassword) {
            return User.builder()
                    .email(this.email)
                    .agreement(this.agreement)
                    .nickname(this.nickname)
                    .password(encodedPassword)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class LoginRequest {
        @Schema(description = "본인인증 식별자", example = "random_string")
        String verificationCode;
        @Length()
        @Schema(description = "비밀번호", example = ExampleValue.User.PASSWORD)
        String password;
    }
}
