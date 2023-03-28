package projectbuildup.mivv.domain.challenge.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping("/specific")
    public ResponseEntity<HttpStatus> getAllChallenges(){
        challengeService.getChallengeSpecificAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{ challengeId }")
    public ResponseEntity<HttpStatus> getChallenge(ChallengeRequestDto.ReadRequest challengeRequestDto, int challengeId){


    }

}
