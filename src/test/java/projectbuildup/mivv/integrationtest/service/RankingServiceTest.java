package projectbuildup.mivv.integrationtest.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class RankingServiceTest {
    @Autowired
    private StringRedisTemplate redisTemplate;
    static ZSetOperations<String, String> operations;
    private final static String TEST_KEY = "0";

    @BeforeEach
    void beforeEach() {
        redisTemplate.delete(TEST_KEY);
        operations = redisTemplate.opsForZSet();
    }

    @Test
    @DisplayName("zset에 원소를 추가한다.")
    void test1() {
        operations.add(TEST_KEY, "1", 100);

        Double result = operations.score(TEST_KEY, "1");
        assertThat(result).isEqualTo(100);
    }

    @Test
    @DisplayName("점수의 내림차순으로 모든 원소를 조회한다.")
    void test2() {
        operations.add(TEST_KEY, "1", 100);
        operations.add(TEST_KEY, "2", 200);
        operations.add(TEST_KEY, "3", 300);
        Set<String> set = operations.reverseRange(TEST_KEY, 0, -1);
        assertThat(set).containsExactly("3", "2", "1");
    }

    @Test
    @DisplayName("기존 점수에 양수를 더한다.")
    void test3() {
        operations.add(TEST_KEY, "1", 100);
        operations.incrementScore(TEST_KEY, "1", 200);
        Double result = operations.score(TEST_KEY, "1");
        assertThat(result).isEqualTo(300);
    }

    @Test
    @DisplayName("기존 점수에 음수를 더한다.")
    void test4() {
        operations.add(TEST_KEY, "1", 100);
        operations.incrementScore(TEST_KEY, "1", -50);
        Double result = operations.score(TEST_KEY, "1");
        assertThat(result).isEqualTo(50);
    }

    @Test
    @DisplayName("원소가 없는 경우 null을 반환한다.")
    void test5() {
        Double result = operations.score(TEST_KEY, "1");
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("현재 1등을 조회한다.")
    void test6() {
        //given
        operations.add(TEST_KEY, "1", 100);
        operations.add(TEST_KEY, "2", 200);
        operations.add(TEST_KEY, "3", 300);

        //when
        TypedTuple<String> tuple = operations.popMax(TEST_KEY);
        String value = tuple.getValue();
        Double score = tuple.getScore();
        assert value != null;
        assert score != null;
        operations.add(TEST_KEY, value, score);
        Set<String> set = operations.reverseRange(TEST_KEY, 0, -1);

        //then
        assertThat(value).isEqualTo("3");
        assertThat(score).isEqualTo(300);
        assertThat(set).containsExactly("3", "2", "1");
    }

    @Test
    @DisplayName("등수를 조회한다.(0부터 시작)")
    void test7() {
        // given
        operations.add(TEST_KEY, "1", 100);
        operations.add(TEST_KEY, "2", 200);
        operations.add(TEST_KEY, "3", 300);

        // when
        Long rank = operations.reverseRank(TEST_KEY, "3");

        // then
        assertThat(rank).isEqualTo(0);
    }

    @Test
    @DisplayName("자신 위아래 2명을 함께 조회한다.")
    void test8() {
        // given
        operations.add(TEST_KEY, "1", 10);
        operations.add(TEST_KEY, "2", 20);
        operations.add(TEST_KEY, "3", 30);
        operations.add(TEST_KEY, "4", 40);
        operations.add(TEST_KEY, "5", 50);
        operations.add(TEST_KEY, "6", 60);
        operations.add(TEST_KEY, "7", 70);
        operations.add(TEST_KEY, "8", 80);
        operations.add(TEST_KEY, "9", 90);
        final String TARGET = "8";

        // when
        Set<String> tupleSet = operations.reverseRange(TEST_KEY, 0, -1);
        List<String> tupleList = tupleSet.stream().toList();

        int targetIdx = tupleList.indexOf(TARGET);
        List<String> result = new ArrayList<>();
        for (int i = targetIdx - 2; i <= targetIdx + 2; i++) {
            result.add(get(tupleList, i));
        }

        // then
        assertThat(result).containsExactly(null, "9", "8", "7", "6");
    }

    @Test
    @DisplayName("송금액과 참여횟수를 바탕으로 순위를 매긴다.")
    void test9() {
        // given
        long amount1 = 10000;
        int count1 = 10;
        long amount2 = 20000;
        int count2 = 20;
        long amount3 = 20000;
        int count3 = 30;
        long amount4 = 20000;
        int count4 = 40;
        long amount5 = 30000;
        int count5 = 50;

        operations.add(TEST_KEY, "1", calcScore(amount1, count1));
        operations.add(TEST_KEY, "2", calcScore(amount2, count2));
        operations.add(TEST_KEY, "3", calcScore(amount3, count3));
        operations.add(TEST_KEY, "4", calcScore(amount4, count4));
        operations.add(TEST_KEY, "5", calcScore(amount5, count5));

        // when
        Set<String> tupleSet = operations.reverseRange(TEST_KEY, 0, -1);

        // then
        assertThat(tupleSet).containsExactly("5", "4", "3", "2", "1");
    }

    private String get(List<String> list, int idx) {
        try {
            return list.get(idx);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private double calcScore(long amount, int count) {
        return amount + (double) count / 1000;
    }

    @Test
    @DisplayName("increment로 값을 초기화할 수 있다.")
    void test10() {
        // given
        operations.incrementScore(TEST_KEY, "1", 1000);

        //when
        Double score = operations.score(TEST_KEY, "1");

        // then
        assertThat(score).isEqualTo(1000);
    }

    @Test
    @DisplayName("삭제하려는 key가 없어도 오류가 발생하지 않는다.")
    void test11() {
        // given
        redisTemplate.delete("test");
        // when

        // then
    }
    @Test
    @DisplayName("점수가 null이면 0을 반환한다. ")
    void test12() {
        // given
        // when
        Long score = null;

        // then
        assertThat(Objects.requireNonNullElse(score, 0L)).isEqualTo(0);

    }

}
