package projectbuildup.mivv.domain.challenge.service;

import lombok.RequiredArgsConstructor;
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
import java.util.stream.Collectors;

import static projectbuildup.mivv.domain.challenge.service.RedisRankingSystem.*;

@RequiredArgsConstructor
@Service
public class RankingService {
    private final RedisRankingSystem rankingSystem;
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final RemittanceRepository remittanceRepository;
    private final ParticipationRepository participationRepository;
    public final static String TOTAL_RANKING_KEY = "TOTAL";

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

    private RankDto.GroupResponse generateResponse(RankDto.UnitResponse theFirst, List<RankDto.UnitResponse> userRanking) {
        List<RankDto.UnitResponse> upper = userRanking.subList(0, NEARBY_SIZE).stream().filter(Objects::nonNull).toList();
        RankDto.UnitResponse me = userRanking.get(NEARBY_SIZE);
        List<RankDto.UnitResponse> lower = userRanking.subList(NEARBY_SIZE + 1, NEARBY_SIZE + 3).stream().filter(Objects::nonNull).toList();
        return new RankDto.GroupResponse(theFirst, upper, me, lower);
    }

    private RankDto.UnitResponse toResponseDto(RankDto.Unit rank, Challenge challenge) {
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
     * 전체 랭킹 및 챌린지 랭킹의 점수를 갱신합니다.
     *
     * @param user      사용자
     * @param challenge 챌린지
     * @param score     점수
     */
    public void updateScore(User user, Challenge challenge, double score) {
        String key = String.valueOf(challenge.getId());
        String member = String.valueOf(user.getId());
        rankingSystem.incrementScore(key, member, score);
        rankingSystem.incrementScore(TOTAL_RANKING_KEY, member, score);
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
            Long rank = rankingSystem.getRank(key, member);
            Challenge challenge = participation.getChallenge();
            rankList.add(new RankDto.ShortResponse(challenge.getId(), challenge.getMainTitle(), rank));
        }
        return rankList;
    }
}
