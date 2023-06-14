package projectbuildup.mivv.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.challenge.dto.RankDto;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.participation.repository.ParticipationRepository;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CResourceNotFoundException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static projectbuildup.mivv.domain.challenge.service.RedisRankingSystem.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class RankingService {
    private final RedisRankingSystem rankingSystem;
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final RemittanceRepository remittanceRepository;
    private final ParticipationRepository participationRepository;
    public final static String TOTAL_RANKING_KEY = "TOTAL";
    public final static String PREFIX = "RANKING_";

    /**
     * 전체 랭킹을 조회합니다.
     * 현재 1등과 사용자의 위 아래 (NEARBY_SIZE) 만큼의 랭킹을 반환합니다.
     *
     * @param userId 사용자 아이디넘버
     * @return 전체 랭킹 목록
     */
    public RankDto.GroupResponse getTotalRanking(Long userId) {
        String member = String.valueOf(userId);
        RankDto.UnitResponse theFirst = toResponseDto(rankingSystem.getTheFirst(TOTAL_RANKING_KEY));
        List<RankDto.UnitResponse> userRanking = rankingSystem.getNearbyRanking(TOTAL_RANKING_KEY, member).stream()
                .map(this::toResponseDto)
                .toList();
        return generateResponse(theFirst, userRanking);
    }

    /**
     * 챌린지 랭킹을 조회합니다.
     * 현재 1등과 사용자의 위 아래 (NEARBY_SIZE) 만큼의 랭킹을 반환합니다.
     *
     * @param challengeId 챌린지 아이디넘버
     * @param userId      사용자 아이디넘버
     * @return 챌린지 랭킹 목록
     */
    public RankDto.GroupResponse getChallengeRanking(Long challengeId, Long userId) {
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(CResourceNotFoundException::new);
        String key = String.valueOf(challengeId);
        String member = String.valueOf(userId);
        RankDto.UnitResponse theFirst = toResponseDto(rankingSystem.getTheFirst(key), challenge);
        List<RankDto.UnitResponse> userRanking = rankingSystem.getNearbyRanking(key, member).stream()
                .map(r -> toResponseDto(r, challenge))
                .toList();
        return generateResponse(theFirst, userRanking);
    }

    /**
     * 사용자의 전체 랭킹 순위를 조회합니다.
     *
     * @param user 사용자
     * @return 전체 랭킹 순위
     */
    public Long getTotalRank(User user) {
        String member = String.valueOf(user.getId());
        return rankingSystem.getUserRank(TOTAL_RANKING_KEY, member);
    }

    /**
     * 등수 리스트를 본인을 기준으로 first, upper, me, lower 새 필드에 나누어 담습니다.
     *
     * @param theFirst    1등의 랭킹 정보
     * @param userRanking 자신 근처 랭킹 정보
     * @return first, upper, me, lower
     */
    private RankDto.GroupResponse generateResponse(RankDto.UnitResponse theFirst, List<RankDto.UnitResponse> userRanking) {
        if (theFirst == null) {
            return null;
        }
        // 하위 정보를 주도록 해야함
        if (userRanking == null) {
            return new RankDto.GroupResponse(theFirst, null, null, null);
        }
        List<RankDto.UnitResponse> upper = userRanking.subList(0, NEARBY_SIZE).stream().filter(Objects::nonNull).toList();
        RankDto.UnitResponse me = userRanking.get(NEARBY_SIZE);
        List<RankDto.UnitResponse> lower = userRanking.subList(NEARBY_SIZE + 1, NEARBY_SIZE + 3).stream().filter(Objects::nonNull).toList();
        return new RankDto.GroupResponse(theFirst, upper, me, lower);
    }

    private RankDto.UnitResponse toResponseDto(RankDto.Unit rank, Challenge challenge) {
        if (rank == null) {
            return null;
        }
        User user = userRepository.findById(rank.getUserId()).orElseThrow(CUserNotFoundException::new);
        Long sumAmount = remittanceRepository.findSumAmountByUserAndChallenge(user, challenge);
        return RankDto.UnitResponse.builder()
                .rank(rank.getRank())
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .amount(Objects.requireNonNullElse(sumAmount, 0L))
                .build();
    }

    private RankDto.UnitResponse toResponseDto(RankDto.Unit rank) {
        if (rank == null) {
            return null;
        }
        User user = userRepository.findById(rank.getUserId()).orElseThrow(CUserNotFoundException::new);
        Long sumAmount = remittanceRepository.findSumAmountByUser(user);
        return RankDto.UnitResponse.builder()
                .rank(rank.getRank())
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .amount(Objects.requireNonNullElse(sumAmount, 0L))
                .build();
    }


    /**
     * 사용자가 참여중인 모든 챌린지에 대한 등수를 조회합니다. (본인 등수만 조회)
     * 아직 송금 기록이 없는 경우, null을 리턴합니다.
     *
     * @param user 사용자
     * @return 등수 정보
     */
    public List<RankDto.ShortResponse> getUserRanks(User user) {
        List<Participation> participations = participationRepository.findAllByUser(user);
        List<RankDto.ShortResponse> rankList = new ArrayList<>();
        for (Participation participation : participations) {
            String key = String.valueOf(participation.getChallenge().getId());
            String member = String.valueOf(user.getId());
            Challenge challenge = participation.getChallenge();
            Long challengeRank = rankingSystem.getParticipationRank(key, member);
            rankList.add(new RankDto.ShortResponse(challenge.getId(), challenge.getMainTitle(), challengeRank));
        }
        return rankList;
    }


    /**
     * 전체 랭킹 및 챌린지 랭킹의 점수를 갱신합니다.
     *
     * @param participation 참여 정보
     * @param score     점수
     */
    public void updateScore(Participation participation, double score) {
        String key = String.valueOf(participation.getChallenge().getId());
        String member = String.valueOf(participation.getUser().getId());
        rankingSystem.incrementScore(key, member, score);
        rankingSystem.incrementScore(TOTAL_RANKING_KEY, member, score);
    }


    /**
     * 신규 사용자의 전체 랭킹 점수를 초기화합니다.
     * 전체 랭킹 점수 0점을 저장합니다.
     *
     * @param user 사용자
     */
    public void initUserRank(User user) {
        String member = String.valueOf(user.getId());
        rankingSystem.removeKeyAndMember(TOTAL_RANKING_KEY, member);
        rankingSystem.incrementScore(TOTAL_RANKING_KEY, member, 0);
    }

    /**
     * 챌린지 랭킹 점수를 초기화합니다.
     * 챌린지 랭킹 점수 0점을 저장합니다.
     *
     * @param participation 참여 정보
     */
    public void initParticipationRank(Participation participation) {
        String key = String.valueOf(participation.getChallenge().getId());
        String member = String.valueOf(participation.getUser().getId());
        rankingSystem.removeKeyAndMember(key, member);
        rankingSystem.incrementScore(key, member, 0);
    }

    /**
     * 계좌 초기화 시 사용자의 전체 랭킹 정보를 삭제합니다.
     *
     * @param user 사용자
     */
    public void resetUserRank(User user) {
        String member = String.valueOf(user.getId());
        rankingSystem.removeKeyAndMember(TOTAL_RANKING_KEY, member);
    }

    /**
     * 챌린지 포기 시, 사용자의 챌린지 랭킹 정보를 삭제합니다.
     * 삭제된 점수만큼 전체 랭킹 점수도 재조정됩니다.
     *
     * @param participation 참여 정보
     */
    public void resetParticipationRank(Participation participation) {
        String key = String.valueOf(participation.getChallenge().getId());
        String member = String.valueOf(participation.getUser().getId());
        double score = rankingSystem.removeKeyAndMember(key, member).orElse(0.0);
        rankingSystem.incrementScore(TOTAL_RANKING_KEY, member, -score);
    }

}
