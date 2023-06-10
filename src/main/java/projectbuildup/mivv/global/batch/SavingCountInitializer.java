package projectbuildup.mivv.global.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.participation.repository.ParticipationRepository;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SavingCountInitializer {
    private final ParticipationRepository participationRepository;
    private static final long FIXED_ONE_DAY = 1000 * 60 * 60 * 24;
    private static final long FIXED_ONE_MINUTE = 1000 * 60;
    private static final String CRON_MIDNIGHT = "0 0 0 * * *";

    @Scheduled(cron = CRON_MIDNIGHT)
    private void renewWithdraw() {
        log.info("참여 횟수 초기화 시작");
        List<Participation> participations = participationRepository.findAll();
        for (Participation participation : participations) {
            participation.initialize();
        }
        participationRepository.saveAll(participations);
        log.info("참여 횟수 초기화 종료");
    }
}

