package projectbuildup.mivv.integrationtest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.auth.dto.AuthDto;
import projectbuildup.mivv.domain.auth.dto.VerificationResponseDto;
import projectbuildup.mivv.domain.user.dto.PasswordChangeDto;
import projectbuildup.mivv.domain.user.dto.ProfileDto;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.DeviceType;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;
import projectbuildup.mivv.global.utils.DeviceFinder;
import projectbuildup.mivv.integrationtest.setting.IntegrationTest;
import projectbuildup.mivv.integrationtest.setting.WithAuthUser;

import java.io.IOException;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserApiTest extends IntegrationTest {
    static final String SEND_LINK_API = "/api/password/change";
    static final String CONFIRM_CHANGE_API = "/api/password/change";
    static final String CHANGE_PASSWORD_API = "/api/password/change";
    static final String GET_PROFILE = "/api/profile";
    static final String UPDATE_PROFILE = "/api/profile";


    @Test
    @Disabled
    @WithAuthUser(role = "USER", id = "1")
    @DisplayName("비밀번호 재설정 링크를 전송한다.")
    void test1() throws Exception {
        // given

        //when
        ResultActions actions = mvc.perform(post(SEND_LINK_API));

        //then
        actions.andExpect(status().isOk());
    }
}
