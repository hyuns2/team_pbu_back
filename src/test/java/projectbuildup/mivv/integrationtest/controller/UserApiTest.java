package projectbuildup.mivv.integrationtest.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import projectbuildup.mivv.integrationtest.setting.IntegrationTest;
import projectbuildup.mivv.integrationtest.setting.WithAuthUser;

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
