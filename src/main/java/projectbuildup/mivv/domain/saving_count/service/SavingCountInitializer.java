package projectbuildup.mivv.domain.saving_count.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SavingCountInitializer {
    private final SavingCountService savingCountService;
    private static final long FIXED_ONE_DAY = 1000 * 60 * 60 * 24;
    private static final long FIXED_ONE_MINUTE = 1000 * 60;
    private static final String CRON_DAYBREAK = "0 0 4 * * *";

    @Scheduled(fixedRate = FIXED_ONE_DAY)
    private void renewWithdraw() {
        log.info("참여 횟수 초기화 시작");
        savingCountService.initializeToZero();
        log.info("참여 횟수 초기화 종료");
    }
}

