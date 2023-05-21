package projectbuildup.mivv.integrationtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import projectbuildup.mivv.integrationtest.setting.IntegrationTest;
import projectbuildup.mivv.integrationtest.setting.WithAuthUser;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RankingApiTest extends IntegrationTest {
    final static String CHALLENGE_RANKING_API = "/api/ranking/challenge/{challengeId}";
    final static String WHOLE_RANKING_API = "/api/ranking";


    @Test
    @WithAuthUser(role = "USER", id = "1")
    @DisplayName("전체 랭킹 조회 - 본인이 1등인 경우")
    void test() throws Exception {
        // given
        // when
        ResultActions actions = mvc.perform(get(WHOLE_RANKING_API));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("upper").isEmpty())
                .andExpect(jsonPath("me.rank").value(1))
                .andExpect(jsonPath("lower[0].rank").value(2))
                .andExpect(jsonPath("lower[1].rank").value(3));
    }

    @Test
    @WithAuthUser(role = "USER", id = "9")
    @DisplayName("전체 랭킹 조회 - 본인이 2등인 경우")
    void test1() throws Exception {
        // given
        // when
        ResultActions actions = mvc.perform(get(WHOLE_RANKING_API));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("upper", hasSize(1)))
                .andExpect(jsonPath("upper[0].rank").value(1))
                .andExpect(jsonPath("me.rank").value(2))
                .andExpect(jsonPath("lower[0].rank").value(3))
                .andExpect(jsonPath("lower[1].rank").value(4));
    }

    @Test
    @WithAuthUser(role = "USER", id = "8")
    @DisplayName("전체 랭킹 조회 - 본인이 3등인 경우")
    void test2() throws Exception {
        // given
        // when
        ResultActions actions = mvc.perform(get(WHOLE_RANKING_API));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("upper[0].rank").value(1))
                .andExpect(jsonPath("upper[1].rank").value(2))
                .andExpect(jsonPath("me.rank").value(3))
                .andExpect(jsonPath("lower[0].rank").value(4))
                .andExpect(jsonPath("lower[1].rank").value(5));
    }

    @Test
    @WithAuthUser(role = "USER", id = "3")
    @DisplayName("전체 랭킹 조회 - 본인이 뒤에서 2등인 경우")
    void test3() throws Exception {
        // given
        // when
        ResultActions actions = mvc.perform(get(WHOLE_RANKING_API));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("upper[0].rank").value(6))
                .andExpect(jsonPath("upper[1].rank").value(7))
                .andExpect(jsonPath("me.rank").value(8))
                .andExpect(jsonPath("lower", hasSize(1)))
                .andExpect(jsonPath("lower[0].rank").value(9));
    }

    @Test
    @WithAuthUser(role = "USER", id = "2")
    @DisplayName("전체 랭킹 조회 - 본인이 꼴등인 경우")
    void test4() throws Exception {
        // given
        // when
        ResultActions actions = mvc.perform(get(WHOLE_RANKING_API));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("upper[0].rank").value(7))
                .andExpect(jsonPath("upper[1].rank").value(8))
                .andExpect(jsonPath("me.rank").value(9))
                .andExpect(jsonPath("lower").isEmpty());
    }
}
