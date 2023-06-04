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
import java.util.Optional;
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
    public Future<Boolean> remit(RemittanceDto.RemitRequest requestDto, Optional<LocalDateTime> currentTime) {
        Challenge challenge = challengeRepository.findById(requestDto.getChallengeId()).orElseThrow(CResourceNotFoundException::new);
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(CUserNotFoundException::new);
        Participation participation = participationRepository.findByChallengeAndUser(challenge, user).orElseThrow(() -> new CBadRequestException("참여 중인 챌린지에만 송금할 수 있습니다."));
        validate(challenge, participation, requestDto.getAmount());
        return executorService.submit(() -> remittanceChecker.check(requestDto.getAmount(), participation, currentTime.orElse(LocalDateTime.now())));
    }

    public Future<Boolean> remitTest(RemittanceDto.RemitRequest requestDto, Optional<LocalDateTime> currentTime) {
        Challenge challenge = challengeRepository.findById(requestDto.getChallengeId()).orElseThrow(CResourceNotFoundException::new);
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(CUserNotFoundException::new);
        Participation participation = participationRepository.findByChallengeAndUser(challenge, user).orElseThrow(() -> new CBadRequestException("참여 중인 챌린지에만 송금할 수 있습니다."));
        return executorService.submit(() -> remittanceChecker.checkTest(requestDto.getAmount(), participation, currentTime.orElse(LocalDateTime.now())));
    }

    /**
     * 송금할 수 있는 상태인지 검증합니다.
     * 참여 중인 챌린지가 아닌 경우 CSavingCountOverException을 던집니다.
     * 챌린지의 최소 송금 금액과 최대 송금 금액을 만족하지 않는 경우 CIllegalArgumentException을 던집니다.
     *
     * @param challenge     챌린지
     * @param participation 참여 정보
     * @param amount        송금 금액
     */
    private void validate(Challenge challenge, Participation participation, long amount) {
        if (!participation.canRemit()) {
            throw new CSavingCountOverException();
        }
        if (!challenge.canRemit(amount)) {
            throw new CIllegalArgumentException("송금할 수 있는 금액의 범위를 벗어났습니다.");
        }
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
     * yearMonth가 empty인 경우, 현재 달을 설정합니다.
     *
     * @param user 사용자
     * @return 총 절약 금액, 현재 달의 절약 횟수, 랭킹 정보 (전체 랭킹 + 챌린지별 랭킹)
     */
    public RemittanceDto.StatusResponse getBriefStatus(User user, Optional<YearMonth> yearMonth) {
        long totalAmount = getTotalAmount(user);
        RemittanceCount monthlyCount = getMonthlyCount(user, yearMonth.orElse(YearMonth.now()));
        List<RankDto.ShortResponse> userRanks = getUserRanks(user);
        return new RemittanceDto.StatusResponse(totalAmount, monthlyCount, userRanks);
    }

    /**
     * 사용자의 총 절약 금액을 반환합니다.
     *
     * @param user 사용자
     * @return 총 절약 금액
     */
    private long getTotalAmount(User user) {
        return remittanceRepository.findSumAmountByUser(user);
    }

    /**
     * 사용자가 특정 월에 절약한 횟수를 반환합니다.
     *
     * @param user 사용자
     * @param now  월 (yyyy-MM)
     * @return 특정 월에 절약한 횟수
     */
    private RemittanceCount getMonthlyCount(User user, YearMonth now) {
        LocalDateTime startTime = now.atDay(1).atStartOfDay();
        LocalDateTime endTime = now.atEndOfMonth().atTime(LocalTime.MAX);
        long count = remittanceRepository.findByUserAndDepositAndCreatedTimeBetween(user, startTime, endTime).size();
        return new RemittanceCount(now, count);
    }

    /**
     * 사용자의 전체/챌린지별 랭크를 리스트로 반환합니다.
     * 리스트의 0번째 인덱스에는 전체 순위가 담깁니다.
     * 리스트의 1번째 인덱스부터 사용자가 참여중인 챌린지별 순위가 담깁니다.
     *
     * @param user 사용자
     * @return 전체/챌린지별 랭크
     */
    private List<RankDto.ShortResponse> getUserRanks(User user) {
        List<RankDto.ShortResponse> ranks = new ArrayList<>();
        RankDto.ShortResponse totalRank = new RankDto.ShortResponse(0L, "전체", rankingService.getTotalRank(user));
        List<RankDto.ShortResponse> challengeRanks = rankingService.getUserRanks(user);
        ranks.add(totalRank);
        ranks.addAll(challengeRanks);
        return ranks;
    }

    /**
     * 지날 달의 절약 금액을 조회합니다.
     * prevMonth가 empty인 경우, 현재 시각을 기준으로 이전 달을 설정합니다.
     *
     * @param userId    사용자 아이디넘버
     * @param prevMonth yyyyMM 형식
     * @return 지난 달의 절약 금액
     */
    public long getPreviousSavingAmount(Long userId, Optional<YearMonth> prevMonth) {
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        YearMonth yearMonth = prevMonth.orElse(YearMonth.now().minusMonths(1));
        LocalDateTime startTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endTime = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);
        return remittanceRepository.findSumAmountByUserAndCreatedTimeBetween(user, startTime, endTime);
    }
}
