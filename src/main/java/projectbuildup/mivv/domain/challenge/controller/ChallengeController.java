package projectbuildup.mivv.domain.challenge.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.challenge.dto.request.ChallengeRequestDto;
import projectbuildup.mivv.domain.challenge.dto.response.ChallengeResponseDto;
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
    public ResponseEntity<HttpStatus> getAllChallengesSpecificInfo(){
        challengeService.getChallengeSpecificAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/summary")
    public ResponseEntity<HttpStatus> getAllChallengesSummaryInfo(){
        challengeService.getChallengeSummaryAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{challengeId}/specific")
    public ResponseEntity<ChallengeResponseDto.ReadSpecificResponse> getOneChallengeSpecificInfo(@PathVariable int challengeId){
        challengeValidationService.isExistChallenge(challengeId);
        ChallengeRequestDto.ReadRequest challengeRequestDto =  new ChallengeRequestDto.ReadRequest(challengeId);
        ChallengeResponseDto.ReadSpecificResponse challengeResponseDto = challengeService.getChallengeSpecificOne(challengeRequestDto);
        return new ResponseEntity<>(challengeResponseDto, HttpStatus.OK);
    }

}
