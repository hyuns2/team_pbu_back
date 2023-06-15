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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.challenge.dto.ChallengeDto;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.service.ChallengeParticipationService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.pagination.ChallengePageParam;
import projectbuildup.mivv.global.common.pagination.PageParam;
import projectbuildup.mivv.global.common.pagination.PagingDto;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

@Slf4j
@Tag(name = "[3.Challenge]", description = "챌린지 관련 API입니다.")
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class ChallengeParticipationController {

    private final ChallengeParticipationService challengeParticipationService;

    @Operation(summary="참여 중인 챌린지 목록 조회", description="")
    @Parameter(name = Header.ACCESS_TOKEN, description="액세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/challenges/ongoing")
    public ResponseEntity<PagingDto<ChallengeDto.Response>> getOngoingChallenges(@ParameterObject @Valid ChallengePageParam pageParam, @AuthenticationPrincipal User user){
        PagingDto<ChallengeDto.Response> responseDto = challengeParticipationService.getOngoingChallenges(user.getId(), pageParam);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary="참여 가능한 챌린지 목록 조회", description="")
    @Parameter(name = Header.ACCESS_TOKEN, description="액세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/challenges/joinable")
    public ResponseEntity<PagingDto<ChallengeDto.Response>> getJoinableChallenges(@ParameterObject @Valid ChallengePageParam pageParam, @AuthenticationPrincipal User user){
        PagingDto<ChallengeDto.Response> responseDto = challengeParticipationService.getJoinableChallenges(user.getId(), pageParam);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary="참여 종료된 챌린지 목록 조회", description="사용자가 참여했던 챌린지 중, 종료된 것을 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description="액세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/challenges/closed")
    public ResponseEntity<PagingDto<ChallengeDto.Response>> getClosedChallenges(@ParameterObject @Valid ChallengePageParam pageParam, @AuthenticationPrincipal User user){
        PagingDto<ChallengeDto.Response> responseDto = challengeParticipationService.getClosedChallenges(user.getId(), pageParam);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
