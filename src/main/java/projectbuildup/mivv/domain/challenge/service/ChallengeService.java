package projectbuildup.mivv.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.challenge.dto.request.ChallengeRequestDto;
import projectbuildup.mivv.domain.challenge.dto.response.ChallengeResponseDto;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    /**
    * R :
    * D :
    **/
    public void createChallenge(ChallengeRequestDto.CreationRequest challengeRequestDto){
        Challenge challenge = challengeRequestDto.toEntity();
        challengeRepository.save(challenge);
    }

    public void updateChallenge(ChallengeRequestDto.UpdateRequest challengeRequestDto){
        Challenge challenge = challengeRepository.findById(challengeRequestDto.getId()).orElseThrow();
        challenge.updateChallenge(challengeRequestDto);
        challengeRepository.save(challenge);
    }

}
