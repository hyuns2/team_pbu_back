package projectbuildup.mivv.domain.remittance.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.account.service.accountdetails.AccountDetailsSystem;
import projectbuildup.mivv.domain.account.service.accountdetails.CodefAccountDetailsSystem;
import projectbuildup.mivv.domain.challenge.dto.RankDto;
import projectbuildup.mivv.domain.challenge.service.RankingService;
import projectbuildup.mivv.domain.remittance.dto.RemittanceCount;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.remittance.dto.RemittanceDto;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.participation.repository.ParticipationRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;


@Service
@Slf4j
@RequiredArgsConstructor
public class RemittanceService {
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final ExecutorService executorService;
    private final ParticipationRepository participationRepository;
    private final RemittanceChecker remittanceChecker;
    private final RemittanceRepository remittanceRepository;
    private final RankingService rankingService;

    /**
     * 5분간 계좌 내역을 비동기로 확인하며, 실제 송금이 이루어졌는지 확인합니다.
     * 송금액이 확인된 경우, Remittance 가 생성됩니다.
     *
     * @param requestDto 회원 아이디넘버, 챌린지 아이디넘버, 절약금액
     */
    @Transactional
    public Future<Boolean> remit(RemittanceDto.RemitRequest requestDto, LocalDateTime startTime) {
        Challenge challenge = challengeRepository.findById(requestDto.getChallengeId()).orElseThrow(CResourceNotFoundException::new);
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(CUserNotFoundException::new);
        Participation participation = participationRepository.findByChallengeAndUser(challenge, user).orElseThrow(() -> new CBadRequestException("참여 중인 챌린지에만 송금할 수 있습니다."));
        if (!participation.canRemit()) {
            throw new CSavingCountOverException();
        }
        if (!challenge.canRemit(requestDto.getAmount())) {
            throw new CIllegalArgumentException("송금할 수 있는 금액의 범위를 벗어났습니다.");
        }
        return executorService.submit(() -> remittanceChecker.check(requestDto.getAmount(), participation, startTime));
    }


    /**
     * 해당 연월에 기록된 절약 내역을 모두 조회합니다.
     *
     * @param userId       사용자 아이디넘버
     * @param yearMonthStr yyyyMM
     * @return 절약 내역
     */
    public List<RemittanceDto.DetailsResponse> getRemittanceDetails(Long userId, String yearMonthStr) {
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        YearMonth yearMonth = YearMonth.parse(yearMonthStr, DateTimeFormatter.ofPattern("yyyyMM"));
        LocalDateTime startTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endTime = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);
        return remittanceRepository.findByUserAndCreatedTimeBetween(user, startTime, endTime).stream()
                .map(RemittanceDto.DetailsResponse::new)
                .toList();
    }

    /**
     * 사용자의 절약 상태를 조회합니다.
     *
     * @param user 사용자
     * @return 총 절약 금액, 현재 달의 절약 횟수, 랭킹 정보 (전체 랭킹 + 챌린지별 랭킹)
     */
    public RemittanceDto.StatusResponse getBriefStatus(User user) {
        long totalAmount = getTotalAmount(user);
        RemittanceCount monthlyCount = getMonthlyCount(user, null);

        List<RankDto.ShortResponse> ranks = new ArrayList<>();
        RankDto.ShortResponse totalRank = new RankDto.ShortResponse(0L, "전체", rankingService.getTotalRank(user));
        List<RankDto.ShortResponse> challengeRanks = rankingService.getUserRanks(user);
        ranks.add(totalRank);
        ranks.addAll(challengeRanks);
        return new RemittanceDto.StatusResponse(totalAmount, monthlyCount, ranks);
    }

    private long getTotalAmount(User user) {
        return remittanceRepository.findSumAmountByUser(user);
    }

    private RemittanceCount getMonthlyCount(User user, YearMonth now) {
        if (now == null) {
            now = YearMonth.now();
        }
        LocalDateTime startTime = now.atDay(1).atStartOfDay();
        LocalDateTime endTime = now.atEndOfMonth().atTime(LocalTime.MAX);
        long count = remittanceRepository.findByUserAndDepositAndCreatedTimeBetween(user, startTime, endTime).size();
        return new RemittanceCount(now, count);
    }

}
