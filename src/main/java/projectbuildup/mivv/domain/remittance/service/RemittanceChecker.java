package projectbuildup.mivv.domain.remittance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.account.entity.TransactionDetail;
import projectbuildup.mivv.domain.account.service.accountdetails.AccountDetailsSystem;
import projectbuildup.mivv.domain.archiving.service.RemittanceArchivingService;
import projectbuildup.mivv.domain.challenge.service.RankScoreCalculator;
import projectbuildup.mivv.domain.challenge.service.RankingService;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.saving_count.service.SavingCountService;
import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

@Component
@Slf4j
@RequiredArgsConstructor
public class RemittanceChecker {

    private final AccountDetailsSystem accountDetailsSystem;
    private final RemittanceRepository remittanceRepository;
    private final SavingCountService savingCountService;
    private final RankingService rankingService;
    private final RankScoreCalculator rankScoreCalculator;
    private final RemittanceArchivingService remittanceArchivingService;

    private final static long ASYNC_CHECK_TERM_SEC = 60;
    private final static int ASYNC_CHECK_TRY = 5;

    /**
     * 비동기로 동작하는 작업입니다.
     * 'ASYNC_CHECK_TERM_SEC'간격으로 'ASYNC_CHECK_TRY'횟수만큼 계좌 내역 조회 API를 호출합니다.
     * 송금이 확인된 경우, 송금 정보를 DB에 저장하고, 금일 참여 횟수를 증가시킵니다.
     *
     * @param amount        송금 금액
     * @param participation 참여 정보
     * @throws InterruptedException exception
     */
    public boolean check(long amount, Participation participation, LocalDateTime startTime) throws InterruptedException {
        log.info("5분간 조회 시작");
        if (startTime == null) {
            startTime = LocalDateTime.now();
        }
        for (int i = 0; i < ASYNC_CHECK_TRY; i++) {
            sleep(TimeUnit.MILLISECONDS.convert(ASYNC_CHECK_TERM_SEC, TimeUnit.SECONDS));
            log.info("{}초 경과, 조회 중...", (i + 1) * ASYNC_CHECK_TERM_SEC);
            if (hasRecord(amount, participation.getUser(), startTime)) {
                updateRemittance(amount, participation);
                log.info("송금액 확인 성공");
                return true;
            }
        }
        log.info("송금액 확인 실패");
        return false;
    }

    /**
     * 송금액 확인에 성공할 경우, 실행되는 메서드입니다.
     * - 송금 정보를 DB에 기록합니다.
     * - 금일 절약 횟수를 1 증가시킵니다.
     * - 랭킹 점수를 증가시킵니다.
     * - 챌린지의 총 절약 금액 정보를 갱신합니다.
     *
     * @param amount        송금 정보
     * @param participation 참여 정보
     */
    private void updateRemittance(long amount, Participation participation) {
        Remittance remittance = Remittance.newDeposit(amount, participation);
        remittanceRepository.save(remittance);
        savingCountService.addCount(participation);
        double score = rankScoreCalculator.calculate(remittance);
        rankingService.updateScore(participation.getUser(), participation.getChallenge(), score);
        remittanceArchivingService.assignNumericalConditionCards(participation.getUser());
    }

    /**
     * 조회한 거래 내역 중에서, 사용자가 '절약하기' 버튼을 누른 이후에 송금한 금액이 있는지 확인합니다.
     *
     * @param amount    절약 금액
     * @param user      사용자
     * @param startTime 시작시간
     * @return true/false
     */
    private boolean hasRecord(long amount, User user, LocalDateTime startTime) {
        List<TransactionDetail> transactionDetails = accountDetailsSystem.getDepositHistory(user, startTime.toLocalDate());
        return transactionDetails.stream()
                .filter(t -> t.getTime().isAfter(startTime))
                .anyMatch(t -> t.getAmount() == amount);
    }
}
