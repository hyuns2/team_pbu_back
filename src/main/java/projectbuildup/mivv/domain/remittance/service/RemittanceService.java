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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
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
    private final ExecutorService executorService;

    private final static long ASYNC_CHECK_TERM_SEC = 1;
    private final static int ASYNC_CHECK_TRY = 5;

    /**
     * 절약 정보를 등록합니다.
     * 비동기로 계좌 내역을 확인합니다.
     *
     * @param requestDto 회원 아이디넘버, 챌린지 아이디넘버, 절약금액
     */
    public void remit(RemittanceDto.RemitRequest requestDto, LocalDateTime startTime) {
        Challenge challenge = challengeRepository.findById(requestDto.getChallengeId()).orElseThrow(CResourceNotFoundException::new);
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(CUserNotFoundException::new);
        Remittance remittance = new Remittance(challenge, user, requestDto.getAmount());
        executorService.submit(() -> {
            try {
                check(remittance, user, startTime);
            } catch (InterruptedException e) {
                throw new CInternalServerException();
            }
        });
    }

    /**
     * 비동기로 동작하는 작업입니다.
     * 'ASYNC_CHECK_TERM_SEC'간격으로 'ASYNC_CHECK_TRY'횟수만큼 계좌 내역 조회 API를 호출합니다.
     * 송금이 확인된 경우, 송금 정보를 DB에 저장합니다.
     *
     * @param remittance 송금액
     * @param user       회원
     * @throws InterruptedException exception
     */
    private void check(Remittance remittance, User user, LocalDateTime startTime) throws InterruptedException {
        if (startTime == null) {
            startTime = LocalDateTime.now();
        }
        log.info("송금액 확인 시작");
        for (int i = 0; i < ASYNC_CHECK_TRY; i++) {
            sleep(TimeUnit.MILLISECONDS.convert(ASYNC_CHECK_TERM_SEC, TimeUnit.SECONDS));
            log.info("{}초 경과, 조회 시도", (i + 1) * ASYNC_CHECK_TERM_SEC);
            if (hasRecord(remittance, user, startTime)) {
                remittanceRepository.save(remittance);
                log.info("송금액 확인 성공");
                return;
            }
        }
        log.info("송금액 확인 실패");
    }

    /**
     * 조회한 거래 내역 중에서, 사용자가 '절약하기' 버튼을 누른 이후에 송금한 금액이 있는지 확인합니다.
     *
     * @param remittance 절약
     * @param user       사용자
     * @param startTime  시작시간
     * @return true/false
     */
    private boolean hasRecord(Remittance remittance, User user, LocalDateTime startTime) {
        long amount = remittance.getAmount();
        List<Map<String, String>> history = accountDetailsSystem.getDepositHistory(user);
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
