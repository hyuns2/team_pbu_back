package projectbuildup.mivv.domain.challenge.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import projectbuildup.mivv.domain.challenge.dto.request.ChallengeRequestDto;
import projectbuildup.mivv.domain.challenge.service.ChallengeService;
import projectbuildup.mivv.domain.challenge.service.ChallengeValidationService;

@Slf4j
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
@RestController
public class ChallengeController {

    private final ChallengeService challengeService;
    private final ChallengeValidationService challengeValidationService;
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createChallenge(ChallengeRequestDto.CreationRequest challengeRequestDto){
        challengeService.createChallenge(challengeRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<HttpStatus> getChallenge(ChallengeRequestDto.ReadRequest challengeRequestDto){

    }

}
