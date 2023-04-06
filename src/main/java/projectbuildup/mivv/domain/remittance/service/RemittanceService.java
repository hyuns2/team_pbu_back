package projectbuildup.mivv.domain.remittance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.account.service.accountdetails.AccountDetailsSystem;
import projectbuildup.mivv.domain.account.service.accountdetails.CodefAccountDetailsSystem;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.remittance.dto.RemittanceDto;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CInternalServerException;
import projectbuildup.mivv.global.error.exception.CResourceNotFoundException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;


@Service
@Slf4j
@RequiredArgsConstructor
public class RemittanceService {
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final RemittanceRepository remittanceRepository;

    private final AccountDetailsSystem accountDetailsSystem;


    /**
     * 절약 정보를 등록합니다.
     *
     * @param requestDto 회원 아이디넘버, 챌린지 아이디넘버, 절약금액
     */
    public void remit(RemittanceDto.RemitRequest requestDto) {
        Remittance remittance = createRemittance(requestDto);
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(CUserNotFoundException::new);
        try {
            check(remittance, user);
        } catch (InterruptedException e) {
            throw new CInternalServerException();
        }
    }

    private Remittance createRemittance(RemittanceDto.RemitRequest requestDto) {
        Challenge challenge = challengeRepository.findById(requestDto.getChallengeId()).orElseThrow(CResourceNotFoundException::new);
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(CUserNotFoundException::new);
        return new Remittance(challenge, user, requestDto.getAmount());
    }

    private void check(Remittance remittance, User user) throws InterruptedException {
        LocalDateTime startTime = LocalDateTime.now();
        log.info("송금액 확인 시작");
        for (int i = 0; i <= 5; i++) {
            sleep(TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES));
            // 실제로 송금했는지 확인
            if (hasRecord(remittance, user, startTime)) {
                remittanceRepository.save(remittance);
                log.info("송금액 확인 성공");
                return;
            }
        }
        log.info("송금액 확인 실패");
    }

    private boolean hasRecord(Remittance remittance, User user, LocalDateTime startTime) {
        long amount = remittance.getAmount();
        List<Map<String, String>> history = accountDetailsSystem.getHistory(user);
        return history.stream()
                .filter(map -> {
                    String date = map.get(CodefAccountDetailsSystem.DATE_FIELD);
                    String time = map.get(CodefAccountDetailsSystem.TIME_FIELD);
                    LocalDateTime transferTime = LocalDateTime.parse(date + time, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                    return transferTime.isAfter(startTime);
                })
                .anyMatch(map -> {
                    long transferAmount = Long.parseLong(map.get(CodefAccountDetailsSystem.AMOUNT_FIELD));
                    return transferAmount == amount;
                });
    }
}
