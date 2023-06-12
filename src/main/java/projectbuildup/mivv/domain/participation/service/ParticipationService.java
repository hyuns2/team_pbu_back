package projectbuildup.mivv.domain.participation.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.challenge.service.RankingService;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.participation.repository.ParticipationRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CBadRequestException;
import projectbuildup.mivv.global.error.exception.CResourceNotFoundException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipationService {

    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final ParticipationRepository participationRepository;
    private final RankingService rankingService;

    /**
     * 챌린지에 참여합니다.
     * 이미 참여 중인 경우 예외가 발생합니다.
     *
     * @param challengeId 챌린지 아이디넘버
     * @param userId      회원 아이디넘버
     */
    @Transactional
    public void joinChallenge(Long challengeId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(CResourceNotFoundException::new);
        if (participationRepository.findByChallengeAndUser(challenge, user).isPresent()) {
            throw new CBadRequestException("이미 참여중인 챌린지입니다.");
        }
        Participation participation = new Participation(user, challenge);
        participationRepository.save(participation);
    }


    /**
     * 챌린지를 포기합니다.
     * 절약 정보가 사라지고, 랭킹도 초기화됩니다.
     *
     * @param challengeId 챌린지 아이디넘버
     * @param userId      회원 아이디넘버
     */
    @Transactional
    public void giveUpChallenge(Long challengeId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(CResourceNotFoundException::new);
        Participation participation = participationRepository.findByChallengeAndUser(challenge, user).orElseThrow(() -> new CBadRequestException("참여 중인 챌린지가 아닙니다. "));
        rankingService.resetParticipationRank(user, challenge);
        participationRepository.delete(participation);
    }
}
