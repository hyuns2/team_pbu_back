package projectbuildup.mivv.integrationtest.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.web.servlet.ResultActions;
import projectbuildup.mivv.domain.challenge.service.RankingService;
import projectbuildup.mivv.integrationtest.setting.IntegrationTest;
import projectbuildup.mivv.integrationtest.setting.WithAuthUser;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RankingApiTest extends IntegrationTest {
    final static String CHALLENGE_RANKING_API = "/api/ranking/challenge/{challengeId}";
    final static String WHOLE_RANKING_API = "/api/ranking";
    @Autowired
    private StringRedisTemplate redisTemplate;
    static ZSetOperations<String, String> operations;

    @BeforeEach
    void beforeEach() {
        operations = redisTemplate.opsForZSet();
    }


    @BeforeEach()
    void redisInit() {
        final String CHALLENGE_1_KEY = "RANKING_1";
        final String CHALLENGE_2_KEY = "RANKING_2";
        final String CHALLENGE_3_KEY = "RANKING_3";
        final String TOTAL_RANKING_KEY = "RANKING_TOTAL";
        redisTemplate.delete(CHALLENGE_1_KEY);
        redisTemplate.delete(CHALLENGE_2_KEY);
        redisTemplate.delete(TOTAL_RANKING_KEY);

        operations.add(CHALLENGE_1_KEY, "9", 9.0001);
        operations.add(TOTAL_RANKING_KEY, "9", 9.0001);

        operations.add(CHALLENGE_1_KEY, "8", 8.0001);
        operations.add(TOTAL_RANKING_KEY, "8", 8.0001);

        operations.add(CHALLENGE_1_KEY, "7", 7.0001);
        operations.add(TOTAL_RANKING_KEY, "7", 7.0001);

        operations.add(CHALLENGE_1_KEY, "6", 6.0001);
        operations.add(TOTAL_RANKING_KEY, "6", 6.0001);

        operations.add(CHALLENGE_1_KEY, "5", 5.0001);
        operations.add(TOTAL_RANKING_KEY, "5", 5.0001);

        operations.add(CHALLENGE_1_KEY, "4", 4.0001);
        operations.add(TOTAL_RANKING_KEY, "4", 4.0001);

        operations.add(CHALLENGE_1_KEY, "3", 3.0001);
        operations.add(TOTAL_RANKING_KEY, "3", 3.0001);

        operations.add(CHALLENGE_1_KEY, "2", 2.0001);
        operations.add(TOTAL_RANKING_KEY, "2", 2.0001);

        operations.add(CHALLENGE_1_KEY, "1", 6.0003);
        operations.add(TOTAL_RANKING_KEY, "1", 6.0003);

        operations.add(CHALLENGE_2_KEY, "1", 10.0001);
        operations.add(TOTAL_RANKING_KEY, "1", 10.0001);

        operations.add(CHALLENGE_3_KEY, "1", 0);
        operations.add(TOTAL_RANKING_KEY, "1", 0);
    }

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

    @Test
    @WithAuthUser(role = "USER", id = "1")
    @DisplayName("챌린지 랭킹 조회 - 아직 아무도 송금을 안한 경우")
    void test5() throws Exception {
        // given
        // when
        ResultActions actions = mvc.perform(get(CHALLENGE_RANKING_API, "3"));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("upper[0].rank").value(7))
                .andExpect(jsonPath("upper[1].rank").value(8))
                .andExpect(jsonPath("me.rank").value(9))
                .andExpect(jsonPath("lower").isEmpty());
    }
}
