package projectbuildup.mivv.integrationtest.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import projectbuildup.mivv.DatabaseCleanUp;
import projectbuildup.mivv.domain.auth.dto.AuthDto;
import projectbuildup.mivv.domain.auth.dto.VerificationResponseDto;
import projectbuildup.mivv.domain.auth.repository.IdentityVerificationRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.ErrorCode;
import projectbuildup.mivv.global.error.exception.CUserExistException;
import projectbuildup.mivv.integrationtest.setting.IntegrationTest;
import projectbuildup.mivv.integrationtest.setting.WithAuthUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class AuthApiTest extends IntegrationTest {

    public final static String CERTIFY_API = "/api/auth/certify";
    public final static String SIGNUP_API = "/api/auth/signup";
    public final static String LOGIN_API = "/api/auth/login";
    private final static String LOGOUT_API = "/api/auth/logout";
    private final static String WITHDRAW_API = "/api/auth/withdraw";
    private final static String REISSUE_API = "/api/auth/reissue";

    @Autowired
    DatabaseCleanUp databaseCleanUp;
    @Autowired
    IdentityVerificationRepository identityVerificationRepository;
    @Autowired
    UserRepository userRepository;


    @Test
    @DisplayName("본인인증 - 정상")
    void test() throws Exception {
        // given
        AuthDto.CertifyRequest requestDto = new AuthDto.CertifyRequest("key");

        // when
        ResultActions actions = mvc.perform(post(CERTIFY_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
                .with(csrf()));
        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("verificationCode").isNotEmpty())
                .andExpect(jsonPath("newUser").value(true));
    }

    @Test
    @DisplayName("회원가입 - 정상")
    void test1() throws Exception {
        // given
        AuthDto.CertifyRequest certifyRequest = new AuthDto.CertifyRequest("key");
        String content = mvc.perform(post(CERTIFY_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(certifyRequest))
                        .with(csrf()))
                .andReturn()
                .getResponse()
                .getContentAsString();
        VerificationResponseDto certifyResponse = objectMapper.readValue(content, VerificationResponseDto.class);

        AuthDto.SignupRequest signupRequest = new AuthDto.SignupRequest(certifyResponse.getVerificationCode(), "test@naver.com", "테스트", "123456", true);

        //when
        ResultActions actions = mvc.perform(post(SIGNUP_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest))
                .with(csrf()));

        //then
        actions
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원가입 - 이미 회원인 경우, 예외 반환")
    void test2() throws Exception {
        // given
        User user = userRepository.findById(1L).get();
        AuthDto.SignupRequest requestDto = new AuthDto.SignupRequest(user.getIdentityVerification().getCode(), "test@naver.com", "테스트", "123456", true);

        //when
        ResultActions actions = mvc.perform(post(SIGNUP_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
                .with(csrf()));

        //then
        actions.andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CUserExistException))
                .andExpect(jsonPath("code").value(ErrorCode.MEMBER_EXIST_ERROR.getCode()));
    }

    @Test
    @DisplayName("1번 유저로 로그인한다.")
    void test3() throws Exception {
        // given
        AuthDto.LoginRequest requestDto = new AuthDto.LoginRequest("abcxxxxxabc", "123456");


        // when
        ResultActions actions = mvc.perform(post(LOGIN_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        // then
        actions.andExpect(status().isOk())
                .andExpect((jsonPath("accessToken").exists()))
                .andExpect((jsonPath("refreshToken").exists()));
    }

    @Test
    @WithAuthUser(role = "USER", id = "1")
    @DisplayName("로그아웃한다.")
    void test4() throws Exception {
        // given
        // when
        ResultActions actions = mvc.perform(post(LOGOUT_API)
                .header("ACCESS", "")
                .header("REFRESH", "")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk());
    }

    @Test
    @WithAuthUser(role = "USER", id = "1")
    @DisplayName("회원 탈퇴한다.")
    void test5() throws Exception {
        // given
        // when
        ResultActions actions = mvc.perform(post(WITHDRAW_API)
                .header("ACCESS", "")
                .header("REFRESH", "")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk());
        assertThat(userRepository.findById(1L)).isEmpty();
    }
}
