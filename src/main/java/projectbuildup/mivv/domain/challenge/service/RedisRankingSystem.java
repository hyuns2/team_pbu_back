package projectbuildup.mivv.domain.challenge.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class RedisRankingSystem {
    private final StringRedisTemplate redisTemplate;
    private ZSetOperations<String, String> operations;
    private final static int NEARBY_SIZE = 2;


    @PostConstruct

    void init() {
        operations = redisTemplate.opsForZSet();
    }

    /**
     * 해당 챌린지 랭킹의 절약 점수를 0으로 초기화합니다.
     *
     * @param challengeId key
     * @param userId      member
     */
    public void initZero(Long challengeId, Long userId) {
        String key = String.valueOf(challengeId);
        String member = String.valueOf(userId);
        Double score = operations.score(key, member);
        if (score == null) {
            operations.add(key, member, 0);
            return;
        }
        operations.incrementScore(key, member, -score);
    }

    /**
     * 해당 챌린지 랭킹의 절약 점수를 갱신합니다.
     *
     * @param challengeId key
     * @param userId      member
     */
    public void incrementScore(Long challengeId, Long userId, double score) {
        String key = String.valueOf(challengeId);
        String member = String.valueOf(userId);
        operations.incrementScore(key, member, score);
    }

    /**
     * 해당 챌린지의 1등을 반환합니다.
     *
     * @param challengeId key
     * @return 1등
     */
    public Long getTheFirst(Long challengeId) {
        String key = String.valueOf(challengeId);
        ZSetOperations.TypedTuple<String> tuple = Objects.requireNonNull(operations.popMax(key));
        String member = Objects.requireNonNull(tuple.getValue());
        Double score = Objects.requireNonNull(tuple.getScore());
        operations.add(key, member, score);
        return Long.parseLong(member);
    }

    /**
     * 본인의 랭킹 +- (NEARBY_SIZE)명의 순위를 조회합니다.
     *
     * @param challengeId key
     * @param userId      member
     * @return 랭킹 리스트
     */
    public List<Optional<Long>> getNearbyRanking(Long challengeId, Long userId) {
        String key = String.valueOf(challengeId);
        String member = String.valueOf(userId);

        Set<String> tupleSet = Objects.requireNonNull(operations.reverseRange(key, 0, -1));
        return rankingList(member, tupleSet);
    }

    private List<Optional<Long>> rankingList(String member, Set<String> tupleSet) {
        List<String> tupleList = tupleSet.stream().toList();
        int targetIdx = tupleList.indexOf(member);

        List<Optional<Long>> result = new ArrayList<>();
        for (int i = targetIdx - NEARBY_SIZE; i <= targetIdx + NEARBY_SIZE; i++) {
            result.add(getOptionalLong(tupleList, i));
        }
        return result;
    }

    private Optional<Long> getOptionalLong(List<String> list, int idx) {
        try {
            return Optional.of(Long.parseLong(list.get(idx)));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }

    }
}
