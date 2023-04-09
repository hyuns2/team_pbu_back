package projectbuildup.mivv.domain.challenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.challenge.dto.ChallengeDto;
import projectbuildup.mivv.domain.challenge.service.ChallengeService;
import projectbuildup.mivv.global.common.PagingDto;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class ChallengeController {

    private final ChallengeService challengeService;

    @Operation(summary = "챌린지 개설", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/challenges")
    public ResponseEntity<Void> createChallenge(@RequestBody ChallengeDto.CreationRequest requestDto) {
        challengeService.createChallenge(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "챌린지 전체 조회", description = "")
    @PreAuthorize("permitAll()")
    @GetMapping("/challenges")
    public ResponseEntity<PagingDto<ChallengeDto.Response>> getChallenges(@ParameterObject @PageableDefault(sort = "createdTime") Pageable pageable) {
        PagingDto<ChallengeDto.Response> responseDto = challengeService.getChallenges(pageable);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "챌린지 단건 조회", description = "")
    @PreAuthorize("permitAll()")
    @GetMapping("/challenges/{challengeId}")
    public ResponseEntity<ChallengeDto.Response> getChallenges(@PathVariable Long challengeId) {
        ChallengeDto.Response responseDto = challengeService.getChallenge(challengeId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "챌린지 정보 수정", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/challenges/{challengeId}")
    public ResponseEntity<HttpStatus> updateChallenge(@PathVariable Long challengeId, @RequestBody ChallengeDto.UpdateRequest requestDto) {
        requestDto.setChallengeId(challengeId);
        challengeService.updateChallenge(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "챌린지 삭제", description = "")
    @PreAuthorize("permitAll()")
    @DeleteMapping("/challenges/{challengeId}")
    public ResponseEntity<HttpStatus> deleteChallenge(@PathVariable Long challengeId) {
        challengeService.deleteChallenge(challengeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
