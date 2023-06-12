package projectbuildup.mivv.domain.remittance.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.account.entity.TransactionDetail;
import projectbuildup.mivv.domain.account.service.accountdetails.AccountDetailsSystem;
import projectbuildup.mivv.domain.archiving.service.RemittanceArchivingService;
import projectbuildup.mivv.domain.challenge.dto.RankDto;
import projectbuildup.mivv.domain.challenge.service.RankScoreCalculator;
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
import java.util.Optional;

import static java.lang.Thread.sleep;


@Service
@Slf4j
@RequiredArgsConstructor
public class RemittanceService {
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final ParticipationRepository participationRepository;
    private final RemittanceRepository remittanceRepository;
    private final RankingService rankingService;
    private final RankScoreCalculator rankScoreCalculator;
    private final RemittanceArchivingService remittanceArchivingService;

    @Autowired
    @Qualifier("codefAccountDetailsSystem")
    AccountDetailsSystem accountDetailsSystem;

    @Autowired
    @Qualifier("testAccountDetailsSystem")
    AccountDetailsSystem testAccountDetailsSystem;


    /**
     * 사용자의 계좌 내역을 조회하여 송금액을 갱신합니다.
     * 일일 참여 횟수를 초과한 경우, 예외가 발생합니다.
     *
     * @param requestDto 유저 아이디넘버, 챌린지 아이디넘버, 시작 시간
     * @return true/exception
     */
    @Transactional
    public boolean checkSaving(RemittanceDto.RemitRequest requestDto) {
        Challenge challenge = challengeRepository.findById(requestDto.getChallengeId()).orElseThrow(CResourceNotFoundException::new);
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(CUserNotFoundException::new);
        Participation participation = participationRepository.findByChallengeAndUser(challenge, user)
                .orElseThrow(() -> new CBadRequestException("참여 중인 챌린지에만 송금할 수 있습니다."));

        if (!participation.canRemit()) {
            throw new CBadRequestException("일일 절약 한도를 초과했습니다.");
        }
        TransactionDetail transactionDetail = getRecentTransactionDetail(participation, requestDto.getStartTime());
        updateRemittance(transactionDetail.getAmount(), participation);
        return true;
    }

    /**
     * 챌린지 송금액 범위에 부합하는 가장 최근의 송금 기록을 가져옵니다.
     * 조건에 만족하는 기록을 조회할 수 없으면 예외가 발생합니다.
     *
     * @param participation 참여
     * @param startTime          조회 시작 일자
     * @return 송금 기록
     */
    private TransactionDetail getRecentTransactionDetail(Participation participation, LocalDateTime startTime) {
        User user = participation.getUser();
        Challenge challenge = participation.getChallenge();
        return accountDetailsSystem.getDepositHistory(user, startTime.toLocalDate()).stream()
                .filter(t -> t.isValid(challenge, startTime))
                .max((o1, o2) -> o2.getTime().compareTo(o1.getTime()))
                .orElseThrow(() -> new CBadRequestException("송금이 이루어지지 않았거나, 적합한 금액이 아닙니다."));
    }

    private TransactionDetail getTransactionDetailForTest(Participation participation, LocalDateTime time) {
        User user = participation.getUser();
        if (!participation.canRemit()) {
            throw new CBadRequestException("일일 절약 한도를 초과했습니다.");
        }
        return testAccountDetailsSystem.getDepositHistory(user, time.toLocalDate()).stream()
                .max((o1, o2) -> o2.getTime().compareTo(o1.getTime()))
                .orElseThrow(() -> new CBadRequestException("송금이 이루어지지 않았거나, 적합한 금액이 아닙니다."));
    }

    /**
     * 송금액 조회에 성공할 경우, 실행되는 메서드입니다.
     * - 송금 정보를 DB에 기록합니다.
     * - 금일 절약 횟수를 1 증가시킵니다.
     * - 랭킹 점수를 증가시킵니다.
     * - 챌린지의 총 절약 금액 정보를 갱신합니다.
     * - 수치 조건 카드의 발급을 확인합니다.
     *
     * @param amount        송금액
     * @param participation 참여 정보
     */
    @Transactional
    private void updateRemittance(long amount, Participation participation) {
        Remittance remittance = Remittance.newDeposit(amount, participation);
        remittanceRepository.save(remittance);
        participation.addCount();
        double score = rankScoreCalculator.calculate(remittance);
        rankingService.updateScore(participation.getUser(), participation.getChallenge(), score);
        remittanceArchivingService.assignRemittanceConditionCards(participation.getUser());
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
