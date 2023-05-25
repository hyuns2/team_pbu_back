package projectbuildup.mivv.integrationtest.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import projectbuildup.mivv.integrationtest.setting.IntegrationTest;
import projectbuildup.mivv.integrationtest.setting.WithAuthUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ParticipationApiTest extends IntegrationTest {
    private final static String JOIN_API = "/api/join/challenges/{challengeId}";
    private final static String GIVE_UP_API = "/api/give-up/challenges/{challengeId}";

    @Test
    @WithAuthUser(role = "USER", id = "2")
    @DisplayName("챌린지에 참여한다. ")
    void test() throws Exception {
        // given

        // when
        ResultActions actions = mvc.perform(post(JOIN_API, 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()));

        // then
        actions.andExpect(status().isOk());
    }

    @Test
    @WithAuthUser(role = "USER", id = "1")
    @DisplayName("이미 참여중인 챌린지에 참여하는 경우 예외가 발생한다. ")
    void test1() throws Exception {
        // given

        // when
        ResultActions actions = mvc.perform(post(JOIN_API, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()));

        // then
        actions.andExpect(status().isBadRequest());
    }

    @Test
    @WithAuthUser(role = "USER", id = "1")
    @DisplayName("챌린지를 포기한다.")
    void test2() throws Exception {
        // given

        // when
        ResultActions actions = mvc.perform(post(GIVE_UP_API, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()));

        // then
        actions.andExpect(status().isOk());
    }
}
