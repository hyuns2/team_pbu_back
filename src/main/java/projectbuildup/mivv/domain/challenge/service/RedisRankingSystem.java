package projectbuildup.mivv.domain.challenge.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.challenge.dto.RankDto;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisRankingSystem {
    private final StringRedisTemplate redisTemplate;
    private ZSetOperations<String, String> operations;
    private final static int NEARBY_SIZE = 2;


    @PostConstruct
    void init() {
        operations = redisTemplate.opsForZSet();
    }

    /**
     * 해당 챌린지 랭킹을 초기화합니다.
     *
     * @param key key
     */
    public void initZero(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 해당 챌린지 랭킹의 절약 점수를 갱신합니다.
     *
     * @param key    key
     * @param member member
     * @param score  점수
     */
    public void incrementScore(String key, String member, double score) {
        operations.incrementScore(key, member, score);
    }

    /**
     * 해당 챌린지의 1등을 반환합니다.
     *
     * @param key key
     * @return 1등
     */
    public RankDto.Unit getTheFirst(String key) {
        ZSetOperations.TypedTuple<String> tuple = Objects.requireNonNull(operations.popMax(key));
        String member = Objects.requireNonNull(tuple.getValue());
        Double score = Objects.requireNonNull(tuple.getScore());
        operations.add(key, member, score);
        return new RankDto.Unit(1, Long.parseLong(member));
    }

    /**
     * 본인의 랭킹 +- (NEARBY_SIZE)명의 순위를 조회합니다.
     *
     * @param key    key
     * @param member member
     * @return 랭킹 리스트
     */
    public List<RankDto.Unit> getNearbyRanking(String key, String member) {
        List<String> tupleList = Objects.requireNonNull(operations.reverseRange(key, 0, -1)).stream()
                .toList();
        List<Long> userRanking = getUserRanking(member, tupleList);

        List<RankDto.Unit> result = new ArrayList<>();
        long rank = Objects.requireNonNull(operations.reverseRank(key, member)) + 1 - NEARBY_SIZE;
        for (Long userId : userRanking) {
            addRank(result, rank, userId);
            rank++;
        }
        return result;
    }

    private List<Long> getUserRanking(String member, List<String> tupleList) {
        int targetIdx = tupleList.indexOf(member);

        List<Long> result = new ArrayList<>();
        for (int i = targetIdx - NEARBY_SIZE; i <= targetIdx + NEARBY_SIZE; i++) {
            result.add(getLong(tupleList, i));
        }
        return result;
    }

    private Long getLong(List<String> list, int idx) {
        try {
            return Long.parseLong(list.get(idx));
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private void addRank(List<RankDto.Unit> result, long rank, Long userId) {
        if (userId != null) {
            RankDto.Unit rankData = new RankDto.Unit(rank, userId);
            result.add(rankData);
        }
    }

    /**
     * 해당 챌린지의 등수를 조회합니다.
     *
     * @param key    key
     * @param member member
     * @return 등수
     */
    public Long getRank(String key, String member) {
        Long rank = operations.reverseRank(key, member);
        if (rank == null) {
            return null;
        }
        return rank + 1;
    }
}