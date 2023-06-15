package projectbuildup.mivv.global.batch;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.participation.repository.ParticipationRepository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChallengeDueDateCatcher {

    private static final long FIXED_ONE_DAY = 1000L * 60 * 60 * 24;
    private static final long FIXED_TEN_YEAR = 1000L * 60 * 60 * 24 * 365 * 10;
    private static final long FIXED_ONE_MINUTE = 1000L * 60;
    private static final String CRON_MIDNIGHT = "30 59 23 * * *";
    private final ChallengeRepository challengeRepository;
    private final ParticipationRepository participationRepository;

    @Scheduled(cron = CRON_MIDNIGHT)
    private void updateClosedChallenges() {
        log.info("종료 챌린지 찾기 시작");
        LocalDate today = LocalDate.now();
        List<Challenge> closedChallenges = challengeRepository.findAll().stream()
                .filter(c -> c.getEndDate().isEqual(today))
                .toList();
        for (Challenge challenge : closedChallenges) {
            close(challenge);
        }
        challengeRepository.saveAll(closedChallenges);
        log.info("종료 챌린지 찾기 끝");
    }

    private void close(Challenge challenge) {
        List<Participation> participations = challenge.getParticipationList();
        participations.forEach(Participation::close);
        challenge.close();
    }
}
