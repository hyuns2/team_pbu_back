package projectbuildup.mivv.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;

@Service
@RequiredArgsConstructor
public class ChallengeParticipationService {
    private final ChallengeRepository challengeRepository;

    public void participateChallenge(){

    }
}
