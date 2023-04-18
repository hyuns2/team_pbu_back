package projectbuildup.mivv.domain.challenge.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class RedisTestValueInjector {
    @Autowired
    private StringRedisTemplate redisTemplate;
    static ZSetOperations<String, String> operations;
    private final static String TEST_KEY = "1";

    @BeforeEach
    void beforeEach() {
        redisTemplate.delete(TEST_KEY);
        operations = redisTemplate.opsForZSet();
    }

    @Test
    @DisplayName("테스트 값 주입")
    void test1() {
        operations.add(TEST_KEY, "9", 9000);
        operations.add(TEST_KEY, "8", 8000);
        operations.add(TEST_KEY, "7", 7000);
        operations.add(TEST_KEY, "6", 6000);
        operations.add(TEST_KEY, "5", 5000);
        operations.add(TEST_KEY, "4", 4000);
        operations.add(TEST_KEY, "3", 3000);
        operations.add(TEST_KEY, "2", 2000);
        operations.add(TEST_KEY, "1", 1000);
    }
}
