package projectbuildup.mivv.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.challenge.dto.request.ChallengeRequestDto;
import projectbuildup.mivv.domain.challenge.dto.response.ChallengeResponseDto;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    public void createChallenge(ChallengeRequestDto.CreationRequest challengeRequestDto){
        Challenge challenge = challengeRequestDto.toEntity();
        challengeRepository.save(challenge);
    }
    public ChallengeResponseDto.ReadSummaryResponse getChallengeSummaryOne(ChallengeRequestDto.ReadRequest challengeRequestDto) {
        Challenge challenge = challengeRepository.findById(challengeRequestDto.getId()).orElseThrow();
        return new ChallengeResponseDto.ReadSummaryResponse(challenge);
    }
    public ChallengeResponseDto.ReadSpecificResponse getChallengeSpecificOne(ChallengeRequestDto.ReadRequest challengeRequestDto){
        Challenge challenge = challengeRepository.findById(challengeRequestDto.getId()).orElseThrow();
        return new ChallengeResponseDto.ReadSpecificResponse(challenge);
    }
    public List<ChallengeResponseDto.ReadSummaryResponse> getChallengeSummaryAll(){
        List<Challenge> challenges = challengeRepository.findAll();
        return challenges.stream()
                .map(ChallengeResponseDto.ReadSummaryResponse::new)
                .toList();
    }
    public List<ChallengeResponseDto.ReadSpecificResponse> getChallengeSpecificAll(){
        List<Challenge> challenges = challengeRepository.findAll();
        return challenges.stream()
                .map(ChallengeResponseDto.ReadSpecificResponse::new)
                .toList();
    }
    public void updateChallenge(ChallengeRequestDto.UpdateRequest challengeRequestDto){
        Challenge challenge = challengeRepository.findById(challengeRequestDto.getId()).orElseThrow();
        challenge.updateChallenge(challengeRequestDto);
        challengeRepository.save(challenge);
    }

    public void deleteChallenge(ChallengeRequestDto.DeleteRequest challengeRequestDto){
        try{
            challengeRepository.deleteById(challengeRequestDto.getId());
        } catch (IllegalArgumentException e){
            //throw new CResourceNotFoundException();
        }
    }

}
