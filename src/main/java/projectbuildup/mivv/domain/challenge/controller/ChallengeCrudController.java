package projectbuildup.mivv.domain.challenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.challenge.dto.ChallengeDto;
import projectbuildup.mivv.domain.challenge.service.ChallengeService;
import projectbuildup.mivv.global.common.pagination.ChallengePageParam;
import projectbuildup.mivv.global.common.pagination.PagingDto;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

import java.io.IOException;

@Slf4j
@Tag(name = "[3.Challenge]", description = "챌린지 관련 API입니다.")
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class ChallengeCrudController {

    private final ChallengeService challengeService;

    @Operation(summary = "챌린지 개설", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/challenges")
    public ResponseEntity<Void> createChallenge(@RequestPart ChallengeDto.CreationRequest requestDto, @RequestPart MultipartFile imageFile) throws IOException {
        challengeService.createChallenge(requestDto, imageFile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "챌린지 전체 조회", description = "")
    @PreAuthorize("permitAll()")
    @GetMapping("/challenges")
    public ResponseEntity<PagingDto<ChallengeDto.Response>> getChallenges(@ParameterObject @Valid ChallengePageParam pageParam) {
        PagingDto<ChallengeDto.Response> responseDto = challengeService.getChallenges(pageParam);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "챌린지 단건 조회", description = "")
    @PreAuthorize("permitAll()")
    @GetMapping("/challenges/{challengeId}")
    public ResponseEntity<ChallengeDto.ShortResponse> getChallenges(@PathVariable Long challengeId) {
        ChallengeDto.ShortResponse responseDto = challengeService.getChallenge(challengeId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "챌린지 정보 수정", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/challenges/{challengeId}")
    public ResponseEntity<HttpStatus> updateChallenge(@PathVariable Long challengeId, @RequestPart ChallengeDto.UpdateRequest requestDto, @RequestPart MultipartFile imageFile) throws IOException {
        requestDto.setChallengeId(challengeId);
        challengeService.updateChallenge(requestDto, imageFile);
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
