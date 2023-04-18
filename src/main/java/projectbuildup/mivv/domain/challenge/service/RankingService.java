package projectbuildup.mivv.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.challenge.dto.RankDto;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CResourceNotFoundException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RankingService {
    private final RedisRankingSystem rankingSystem;
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final RemittanceRepository remittanceRepository;

    /**
     * 챌린지 랭킹을 조회합니다.
     * 현재 1등과 사용자의 위 아래 (NEARBY_SIZE) 만큼의 랭킹을 반환합니다.
     *
     * @param challengeId 챌린지 아이디넘버
     * @param userId      사용자 아이디넘버
     * @return 랭킹 목록
     */
    public RankDto.GroupResponse getChallengeRanking(Long challengeId, Long userId) {
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(CResourceNotFoundException::new);
        String key = String.valueOf(challengeId);
        String member = String.valueOf(userId);
        RankDto.UnitResponse theFirst = toResponseDto(rankingSystem.getTheFirst(key), challenge);
        List<RankDto.UnitResponse> userRanking = rankingSystem.getNearbyRanking(key, member).stream()
                .map(r -> toResponseDto(r, challenge))
                .toList();
        return new RankDto.GroupResponse(theFirst, userRanking);
    }

    private RankDto.UnitResponse toResponseDto(RankDto.Unit rank, Challenge challenge) {
        User user = userRepository.findById(rank.getUserId()).orElseThrow(CUserNotFoundException::new);
        long sumAmount = remittanceRepository.findSumAmountByUserAndChallenge(user, challenge);
        return RankDto.UnitResponse.builder()
                .rank(rank.getRank())
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .amount(sumAmount)
                .build();
    }
}
