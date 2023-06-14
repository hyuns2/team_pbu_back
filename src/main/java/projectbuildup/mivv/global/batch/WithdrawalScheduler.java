package projectbuildup.mivv.global.batch;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.account.entity.TransactionDetail;
import projectbuildup.mivv.domain.account.service.accountdetails.AccountDetailsSystem;
import projectbuildup.mivv.domain.challenge.service.RankingService;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.participation.repository.ParticipationRepository;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WithdrawalScheduler {
    @Autowired
    @Qualifier("codefAccountDetailsSystem")
    AccountDetailsSystem accountDetailsSystem;
    private final UserRepository userRepository;
    private final ParticipationRepository participationRepository;
    private final RemittanceRepository remittanceRepository;
    private final RankingService rankingService;

    private static final long FIXED_ONE_DAY = 1000L * 60 * 60 * 24;
    private static final long FIXED_TEN_YEAR = 1000L * 60 * 60 * 24 * 365 * 10;
    private static final long FIXED_ONE_MINUTE = 1000L * 60;
    private static final String CRON_DAYBREAK = "0 0 4 * * *";


    /**
     * 매일 새벽마다 출금액을 반영합니다.
     * 하루 전날의 출금액을 계산합니다.
     * (출금 총액 / 참여중인 챌린지 수)의 금액이 각 챌린지 절약 내역에 마이너스로 저장됩니다.
     */
    @Scheduled(cron = CRON_DAYBREAK)
    private void renewWithdraw() {
        log.info("출금액 동기화 시작");
        LocalDate startDate = LocalDate.now().minusDays(1);
        List<User> users = userRepository.findAll();
        for (User user : users) {
            findAndUpdateWithdrawal(startDate, user);
        }
        log.info("출금액 동기화 종료");
    }

    private void findAndUpdateWithdrawal(LocalDate startDate, User user) {
        List<Participation> participationList = participationRepository.findAllByUser(user);
        long numOfParticipation = participationList.size();
        if (numOfParticipation == 0) {
            return;
        }
        List<TransactionDetail> withdrawHistory = accountDetailsSystem.getWithdrawHistory(user, startDate, startDate);
        long averageAmount = getSum(withdrawHistory, user.getAccount().getCreatedTime()) / numOfParticipation;
        if (averageAmount != 0) {
            saveMinusRemittance(participationList, averageAmount);
        }
    }

    /**
     * 사용자의 계좌 등록 이후에 발생한 출금에 대해 출금액의 총합을 계산합니다.
     *
     * @param withdrawHistory     출금 내역
     * @param accountRegisterTime 사용자의 계좌 등록 시간
     * @return 출금액 합계
     */
    private long getSum(List<TransactionDetail> withdrawHistory, LocalDateTime accountRegisterTime) {
        long sum = 0;
        for (TransactionDetail transactionDetail : withdrawHistory) {
            if (transactionDetail.getTime().isAfter(accountRegisterTime)) {
                sum += transactionDetail.getAmount();
            }
        }
        return sum;
    }

    /**
     * 각 출금액에 대한 Remittance를 생성하고, 랭킹 점수를 갱신합니다.
     *
     * @param participationList 참여 정보 목록
     * @param averageAmount 평균 출금액
     */
    private void saveMinusRemittance(List<Participation> participationList, long averageAmount) {
        for (Participation participation : participationList) {
            Remittance remittance = Remittance.newWithdrawal(participation, -averageAmount);
            rankingService.updateScore(participation, -averageAmount);
            remittanceRepository.save(remittance);
        }
    }
}
