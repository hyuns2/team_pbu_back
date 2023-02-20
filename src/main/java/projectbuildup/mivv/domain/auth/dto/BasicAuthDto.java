package projectbuildup.mivv.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.mivv.domain.member.entity.Member;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.security.jwt.TokenForm;

import java.util.Collections;

public class BasicAuthDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignupRequest{
        @Schema(description = "계정", example = ExampleValue.Member.ACCOUNT)
        String account;

        @Schema(description = "비밀번호", example = ExampleValue.Member.PASSWORD)
        String password;

        /**
         * DTO 내용을 바탕으로 Member 엔티티 생성
         *
         * @return 생성된 Member 엔티티
         */
        public Member toEntity(String encodedPassword) {
            return Member.builder()
                    .account(this.account)
                    .password(encodedPassword)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoginRequest {
        @Schema(description = "계정", example = ExampleValue.Member.ACCOUNT)
        String account;
        @Schema(description = "비밀번호", example = ExampleValue.Member.PASSWORD)
        String password;
    }

    @Getter
    public static class LoginResponse {
        Long memberId;
        TokenForm tokenForm;
        public LoginResponse(Member member, TokenForm tokenForm){
            this.memberId = member.getId();
            this.tokenForm = tokenForm;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class UnlinkRequestDto {
        @NotBlank
        String accessToken;
        @NotBlank
        String refreshToken;
    }

    @Getter
    @NoArgsConstructor
    public static class ReissueRequest {
        @Schema(description = "어세스 토큰", example = ExampleValue.JWT.ACCESS)
        @NotBlank
        String accessToken;
        @Schema(description = "리프레시 토큰", example = ExampleValue.JWT.REFRESH)
        @NotBlank
        String refreshToken;
    }
}
