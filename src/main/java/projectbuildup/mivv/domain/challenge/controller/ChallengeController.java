package projectbuildup.mivv.domain.challenge.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.util.List;

@Slf4j
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
@RestController
@Tag(name = "[Challenge]", description = "챌린지 관련 API입니다.")
public class ChallengeController {

    private final ChallengeService challengeService;
    private final ChallengeValidationService challengeValidationService;
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createChallenge(@RequestBody ChallengeRequestDto.CreationRequest challengeRequestDto){
        challengeService.createChallenge(challengeRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/specific")
    public ResponseEntity<List<ChallengeResponseDto.ReadSpecificResponse>> getAllChallengesSpecificInfo(){
        List<ChallengeResponseDto.ReadSpecificResponse> allChallengesSpecificInfoDto = challengeService.getChallengeSpecificAll();
        return new ResponseEntity<>(allChallengesSpecificInfoDto, HttpStatus.OK);
    }
    @GetMapping("/summary")
    public ResponseEntity<List<ChallengeResponseDto.ReadSummaryResponse>> getAllChallengesSummaryInfo(){
        List<ChallengeResponseDto.ReadSummaryResponse> allChallengesSummaryInfoDto = challengeService.getChallengeSummaryAll();
        return new ResponseEntity<>(allChallengesSummaryInfoDto, HttpStatus.OK);
    }
    @GetMapping("/specific/{challengeId}")
    public ResponseEntity<ChallengeResponseDto.ReadSpecificResponse> getOneChallengeSpecificInfo(@PathVariable int challengeId){
        challengeValidationService.isExistChallenge(challengeId);
        ChallengeRequestDto.ReadRequest challengeRequestDto =  new ChallengeRequestDto.ReadRequest(challengeId);
        ChallengeResponseDto.ReadSpecificResponse challengeResponseDto = challengeService.getChallengeSpecificOne(challengeRequestDto);
        return new ResponseEntity<>(challengeResponseDto, HttpStatus.OK);
    }
    @GetMapping("/summary/{challengeId}")
    public ResponseEntity<ChallengeResponseDto.ReadSummaryResponse> getOneChallengeSummaryInfo(@PathVariable int challengeId){
        challengeValidationService.isExistChallenge(challengeId);
        ChallengeRequestDto.ReadRequest challengeRequestDto =  new ChallengeRequestDto.ReadRequest(challengeId);
        ChallengeResponseDto.ReadSummaryResponse challengeResponseDto = challengeService.getChallengeSummaryOne(challengeRequestDto);
        return new ResponseEntity<>(challengeResponseDto, HttpStatus.OK);
    }
    @PutMapping("/{challengeId}")
    public ResponseEntity<HttpStatus> updateChallenge(@PathVariable int challengeId, @RequestBody ChallengeRequestDto.UpdateRequest challengeRequestDto){
        challengeValidationService.isExistChallenge(challengeId);
        challengeService.updateChallenge(challengeRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{challengeId}")
    public ResponseEntity<HttpStatus> deleteChallenge(@PathVariable int challengeId){
        challengeValidationService.isExistChallenge(challengeId);
        challengeService.deleteChallenge(new ChallengeRequestDto.DeleteRequest(challengeId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
