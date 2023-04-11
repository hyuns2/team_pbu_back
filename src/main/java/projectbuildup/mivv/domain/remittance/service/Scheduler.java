package projectbuildup.mivv.domain.remittance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.account.service.accountdetails.AccountDetailsSystem;
import projectbuildup.mivv.domain.account.service.accountdetails.CodefAccountDetailsSystem;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.participation.repository.ParticipationRepository;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {
    private final AccountDetailsSystem accountDetailsSystem;
    private final UserRepository userRepository;
    private final ParticipationRepository participationRepository;
    private final RemittanceRepository remittanceRepository;

    private static final long FIXED_ONE_DAY = 1000 * 60 * 60 * 24;
    private static final long FIXED_ONE_MINUTE = 1000 * 60;
    private static final String CRON_DAYBREAK = "0 0 4 * * *";


    /**
     * 매일마다 출금액을 반영합니다.
     * (출금 총액 / 참여중인 챌린지 수)의 금액이 각 챌린지 절약 내역에 마이너스로 저장됩니다.
     */
    @Scheduled(fixedRate = FIXED_ONE_DAY)
    private void renewWithdraw() {
        log.info("출금액 동기화 시작");
        long renewed = 0;
        List<User> users = userRepository.findAll();
        for (User user : users) {
            List<Challenge> participatingChallenges = participationRepository.findAllByUser(user).stream()
                    .map(Participation::getChallenge)
                    .toList();
            long numOfParticipation = participatingChallenges.size();
            if (numOfParticipation == 0) {
                continue;
            }
            List<Map<String, String>> withdrawHistory = accountDetailsSystem.getWithdrawHistory(user);
            long sumOfWithdraw = getSum(withdrawHistory);
            long averageWithdrawAmount = sumOfWithdraw / numOfParticipation;
            saveWithdraw(user, participatingChallenges, averageWithdrawAmount);
            renewed++;
        }
        log.info("출금액 동기화 종료({}명의 출금 정보 갱신됨)", renewed);
    }

    private void saveWithdraw(User user, List<Challenge> participatingChallenges, long averageAmount) {
        for (Challenge challenge : participatingChallenges) {
            Remittance remittance = Remittance.builder()
                    .user(user)
                    .challenge(challenge)
                    .amount(-averageAmount)
                    .build();
            remittanceRepository.save(remittance);
        }
    }

    private long getSum(List<Map<String, String>> withdrawHistory) {
        long sum = 0;
        for (Map<String, String> fieldMap : withdrawHistory) {
            long amount = Long.parseLong(fieldMap.get(CodefAccountDetailsSystem.AMOUNT_FIELD));
            sum += amount;
        }
        return sum;
    }
}