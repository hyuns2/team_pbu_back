package projectbuildup.mivv.global.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.challenge.service.RankingService;
import projectbuildup.mivv.domain.challenge.service.RedisRankingSystem;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.participation.repository.ParticipationRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScoreInitializer {
    private final RankingService rankingService;
    private final ParticipationRepository participationRepository;
    private final UserRepository userRepository;
    private static final String CRON_END_OF_MONTH = "0 0 0 L * *";
    private static final long FIXED_ONE_MINUTE = 1000 * 60;


    @Scheduled(cron = CRON_END_OF_MONTH)
    private void init() {
        log.info("전체 랭킹 초기화 시작");
        List<User> users = userRepository.findAll();
        for (User user : users) {
            rankingService.initUserRank(user);
        }
        log.info("전체 랭킹 초기화 종료");

        log.info("챌린지 랭킹 초기화 시작");
        List<Participation> participations = participationRepository.findAll();
        for (Participation participation : participations) {
            rankingService.initParticipationRank(participation);
        }
        log.info("챌린지 랭킹 초기화 종료");
    }
}
