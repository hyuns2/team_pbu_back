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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectbuildup.mivv.domain.participation.service.ParticipationService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api")
@Tag(name = "[1-2.Challenge]", description = "챌린지 참여 관련 API입니다.")
public class ChallengeParticipationController {

    private final ParticipationService participationService;

    @Operation(summary = "챌린지에 참여합니다.", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/join/challenges/{challengeId}")
    public ResponseEntity<HttpStatus> joinChallenge(@PathVariable Long challengeId, @AuthenticationPrincipal User user) {
        participationService.joinChallenge(challengeId, user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "챌린지를 포기합니다.", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/give-up/challenges/{challengeId}")
    public ResponseEntity<HttpStatus> giveUpChallenge(@PathVariable Long challengeId, @AuthenticationPrincipal User user) {
        participationService.giveUpChallenge(challengeId, user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
