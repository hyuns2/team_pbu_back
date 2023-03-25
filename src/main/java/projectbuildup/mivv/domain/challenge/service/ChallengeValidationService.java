package projectbuildup.mivv.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeValidationService {

    private final ChallengeRepository challengeRepository;
    public boolean isJoinableDateChallenge(int challengeId){

        Optional<Challenge> challenge = challengeRepository.findById(challengeId);
        LocalDate todayDate = LocalDate.now();

        if(todayDate.isAfter(challenge.orElseThrow().getStartDate()) && todayDate.isBefore(challenge.orElseThrow().getEndDate()))
            return true;
        else
            return false;


    }
}
