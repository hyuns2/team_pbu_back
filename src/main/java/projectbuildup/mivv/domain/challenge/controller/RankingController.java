package projectbuildup.mivv.domain.challenge.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.challenge.dto.RankDto;
import projectbuildup.mivv.domain.challenge.service.RankingService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

@Slf4j
@Tag(name = "[3.Challenge]", description = "챌린지 관련 API입니다.")
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class RankingController {
    private final RankingService rankingService;

    @Operation(summary="챌린지 랭킹 조회", description="1등과 유저를 포함한 몇 명의 랭킹을 반환합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description="액세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/ranking/challenge/{challengeId}")
    public ResponseEntity<RankDto.GroupResponse> getChallengeRanking(@PathVariable Long challengeId, @AuthenticationPrincipal User user){
        RankDto.GroupResponse responseDto = rankingService.getChallengeRanking(challengeId, user.getId());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary="전체 랭킹 조회", description="1등과 유저를 포함한 몇 명의 랭킹을 반환합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description="액세스토큰", required=true, in= ParameterIn.HEADER, example= ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/ranking")
    public ResponseEntity<RankDto.GroupResponse> getTotalRanking(@AuthenticationPrincipal User user){
        RankDto.GroupResponse responseDto = rankingService.getTotalRanking(user.getId());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
