package projectbuildup.mivv.domain.challenge.service;

import jakarta.annotation.PostConstruct;
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
    public final static int NEARBY_SIZE = 2;
    private final static String PREFIX = "RANKING_";


    @PostConstruct
    void init() {
        operations = redisTemplate.opsForZSet();
    }

    /**
     * 해당 챌린지 랭킹을 초기화합니다.
     *
     * @param key key
     */
    public void initChallengeRanking(String key) {
        redisTemplate.delete(PREFIX + key);
    }

    /**
     * 해당 챌린지 랭킹의 절약 점수를 갱신합니다.
     *
     * @param key    key
     * @param member member
     * @param score  점수
     */
    public void incrementScore(String key, String member, double score) {
        operations.incrementScore(PREFIX + key, member, score);
    }

    /**
     * 해당 챌린지의 1등을 반환합니다.
     *
     * @param key key
     * @return 1등
     */
    public RankDto.Unit getTheFirst(String key) {
        ZSetOperations.TypedTuple<String> tuple = operations.popMax(PREFIX + key);
        if (tuple == null) {
            return null;
        }
        String member = Objects.requireNonNull(tuple.getValue());
        Double score = Objects.requireNonNull(tuple.getScore());
        operations.add(PREFIX + key, member, score);
        return new RankDto.Unit(1, Long.parseLong(member));
    }

    /**
     * 자신의 순위를 조회합니다.
     *
     * @param key    ranking key
     * @param member userId
     * @return 순위
     */
    public Optional<Long> getUserRank(String key, String member) {
        Long rank = operations.reverseRank(PREFIX + key, member);
        if (rank != null) {
            rank++;
        }
        return Optional.ofNullable(rank);
    }

    /**
     * 본인의 랭킹 +- (NEARBY_SIZE)명의 순위를 조회합니다.
     * 위/아래에 순위가 없는 경우 null이 채워져 반환됩니다.
     *
     * @param key    key
     * @param member member
     * @return 랭킹 리스트
     */
    public List<RankDto.Unit> getNearbyRanking(String key, String member) {
        List<RankDto.Unit> result = new ArrayList<>();
        Set<String> set = operations.reverseRange(PREFIX + key, 0, -1);
        if (set == null) {
            return null;
        }
        List<String> totalUserRanking = set.stream().toList();
        Optional<Long> userRank = getUserRank(key, member);
        if (userRank.isEmpty()) {
            return null;
        }

        long currentRank = userRank.get() - NEARBY_SIZE;
        int targetIdx = totalUserRanking.indexOf(member);
        for (int i = targetIdx - NEARBY_SIZE; i <= targetIdx + NEARBY_SIZE; i++) {
            long finalCurrentRank = currentRank++;
            Optional<Long> userId = getUserOptional(totalUserRanking, i);
            result.add(userId.map(u -> new RankDto.Unit(finalCurrentRank, u)).orElse(null));
        }
        return result;
    }


    /**
     * 유효한 인덱스라면 원소를 반환하고, 그렇지 않다면 null을 반환합니다.
     *
     * @param list 리스트
     * @param idx  인덱스
     * @return 데이터 or null
     */
    private Optional<Long> getUserOptional(List<String> list, int idx) {
        final int START_IDX = 0;
        final int END_IDX = list.size();
        if (START_IDX <= idx && idx < END_IDX) {
            return Optional.of(Long.parseLong(list.get(idx)));
        }
        return Optional.empty();
    }


    /**
     * 해당 챌린지의 등수를 조회합니다.
     *
     * @param key    key
     * @param member member
     * @return 등수
     */
    public Long getRank(String key, String member) {
        Long rank = operations.reverseRank(PREFIX + key, member);
        if (rank == null) {
            return null;
        }
        return rank + 1;
    }

    /**
     * 해당 챌린지에서의 회원의 등수 정보를 초기화합니다.
     * 초기화하기 전의 점수를 리턴합니다.
     *
     * @param key    key
     * @param member member
     * @return 점수
     */
    public Optional<Double> initUserRank(String key, String member) {
        Double score = operations.score(key, member);
        if (score == null){
            return Optional.empty();
        }
        operations.remove(key, member);
        return Optional.of(score);
    }
}
