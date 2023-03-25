package projectbuildup.mivv.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.challenge.dto.request.ChallengeRequestDto;
import projectbuildup.mivv.domain.challenge.dto.response.ChallengeResponseDto;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    /*
    * R :
    * U
    * D
    *
    * */
    public void createChallenge(ChallengeRequestDto.CreationRequest challengeRequestDto){
        Challenge challenge = challengeRequestDto.toEntity();
        challengeRepository.save(challenge);
    }

}
