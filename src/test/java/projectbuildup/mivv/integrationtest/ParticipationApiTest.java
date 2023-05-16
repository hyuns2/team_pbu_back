package projectbuildup.mivv.integrationtest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;
import projectbuildup.mivv.integrationtest.setting.IntegrationTest;
import projectbuildup.mivv.integrationtest.setting.MockEntityFactory;
import projectbuildup.mivv.integrationtest.setting.WithAuthUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ParticipationApiTest extends IntegrationTest {
    private final static String JOIN_API = "/api/join/challenges/{challengeId}";
    private final static String GIVE_UP_API = "/api/give-up/challenges/{challengeId}";

    @BeforeEach
    void setup(){

    }

    @Test
    @WithAuthUser(role = "USER")
    @DisplayName("참여 - 정상")
    void test() throws Exception {
        // given
        User user = MockEntityFactory.mockUser(MockEntityFactory.mockIdentityVerification());

        // when
        ResultActions actions = mvc.perform(post(JOIN_API, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()));

        // then
        actions.andExpect(status().isOk());
    }
}
