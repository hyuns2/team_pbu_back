package projectbuildup.mivv.domain.challenge.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

@Slf4j
@RequiredArgsConstructor
public class RedisRankingSystem {
    private final StringRedisTemplate redisTemplate;
    private ZSetOperations<String, String> operations;

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
}
