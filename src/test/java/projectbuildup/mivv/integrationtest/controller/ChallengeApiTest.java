package projectbuildup.mivv.integrationtest.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.integrationtest.setting.IntegrationTest;
import projectbuildup.mivv.integrationtest.setting.WithAuthUser;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChallengeApiTest extends IntegrationTest {

    private static final String DELETE_API = "/api/challenges/{challengeId}";
    @Autowired
    ChallengeRepository challengeRepository;

    @Test
    @WithAuthUser(role = "ADMIN", id = "1")
    @DisplayName("챌린지를 삭제한다.")
    void test() throws Exception {
        // given

        // when
        ResultActions actions = mvc.perform(delete(DELETE_API, 1));

        // then
        actions.andExpect(status().isOk());
        assertThat(challengeRepository.findById(1L)).isEmpty();
    }
}
