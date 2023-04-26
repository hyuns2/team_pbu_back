package projectbuildup.mivv.domain.challenge.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class RedisTestValueInjector {
    @Autowired
    private StringRedisTemplate redisTemplate;
    static ZSetOperations<String, String> operations;
    private final static String CHALLENGE_1_KEY = "1";
    private final static String CHALLENGE_2_KEY = "2";
    private final static String TOTAL_RANKING_KEY = RankingService.TOTAL_RANKING_KEY;

    @BeforeEach
    void beforeEach() {
        operations = redisTemplate.opsForZSet();
    }

    @Test
    @DisplayName("1번 챌린지 랭킹 테스트 값 주입")
    void test1() {
        redisTemplate.delete(CHALLENGE_1_KEY);
        operations.add(CHALLENGE_1_KEY, "9", 9.0001);
        operations.add(CHALLENGE_1_KEY, "8", 8.0001);
        operations.add(CHALLENGE_1_KEY, "7", 7.0001);
        operations.add(CHALLENGE_1_KEY, "6", 6.0001);
        operations.add(CHALLENGE_1_KEY, "5", 5.0001);
        operations.add(CHALLENGE_1_KEY, "4", 4.0001);
        operations.add(CHALLENGE_1_KEY, "3", 3.0001);
        operations.add(CHALLENGE_1_KEY, "2", 2.0001);
        operations.add(CHALLENGE_1_KEY, "1", 6.0003);
    }

    @Test
    @DisplayName("전체 챌린지 랭킹 테스트 값 주입")
    void test2() {
        redisTemplate.delete(TOTAL_RANKING_KEY);
        operations.add(TOTAL_RANKING_KEY, "9", 9.0001);
        operations.add(TOTAL_RANKING_KEY, "8", 8.0001);
        operations.add(TOTAL_RANKING_KEY, "7", 7.0001);
        operations.add(TOTAL_RANKING_KEY, "6", 6.0001);
        operations.add(TOTAL_RANKING_KEY, "5", 5.0001);
        operations.add(TOTAL_RANKING_KEY, "4", 4.0001);
        operations.add(TOTAL_RANKING_KEY, "3", 3.0001);
        operations.add(TOTAL_RANKING_KEY, "2", 2.0001);
        operations.add(TOTAL_RANKING_KEY, "1", 16.0004);
    }

    @Test
    @DisplayName("2번 챌린지 랭킹 테스트 값 주입")
    void test3() {
        redisTemplate.delete(CHALLENGE_2_KEY);
        operations.add(CHALLENGE_2_KEY, "1", 10.0001);
    }
}
