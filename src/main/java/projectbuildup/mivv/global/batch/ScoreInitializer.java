package projectbuildup.mivv.global.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.challenge.service.RankingService;
import projectbuildup.mivv.domain.challenge.service.RedisRankingSystem;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScoreInitializer {
    private final RedisRankingSystem rankingSystem;
    private final ChallengeRepository challengeRepository;
    private static final String CRON_END_OF_MONTH = "0 0 0 L * *";
    private static final long FIXED_ONE_MINUTE = 1000 * 60;


    @Scheduled(cron = CRON_END_OF_MONTH)
    private void init() {
        log.info("랭킹 초기화 시작");
        List<Challenge> challenges = challengeRepository.findAll();
        for (Challenge challenge : challenges) {
            String key = String.valueOf(challenge.getId());
            rankingSystem.initChallengeRanking(key);
        }
        rankingSystem.initChallengeRanking(RankingService.TOTAL_RANKING_KEY);
        log.info("랭킹 초기화 종료");
    }
}
