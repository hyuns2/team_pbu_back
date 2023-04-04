package projectbuildup.mivv.domain.remittance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.remittance.dto.RemittanceDto;
import projectbuildup.mivv.domain.remittance.service.RemittanceService;
import projectbuildup.mivv.domain.user.entity.User;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RemittanceController {
    private final RemittanceService remittanceService;

    @PostMapping("/remit/challenges/{challengeId}")
    public ResponseEntity<Void> remit(@PathVariable Long challengeId, @RequestBody RemittanceDto.RemitRequest requestDto, @AuthenticationPrincipal User user) {
        requestDto.setUserId(user.getId());
        requestDto.setChallengeId(challengeId);
        remittanceService.remit(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
