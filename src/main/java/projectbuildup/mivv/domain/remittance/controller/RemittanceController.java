
package projectbuildup.mivv.domain.remittance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.challenge.service.RankingService;
import projectbuildup.mivv.domain.remittance.dto.RemittanceDto;
import projectbuildup.mivv.domain.remittance.service.RemittanceService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Tag(name = "[4. Remittance]", description = "송금 관련 API입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RemittanceController {
    private final RemittanceService remittanceService;

    @Operation(summary = "절약하기", description = "절약하기 버튼을 누를 때 실행하는 API입니다. 이후 5분간 내역을 조회하며, 실제 송금이 이루어진 경우에만 절약 정보를 갱신합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/remittance/challenges/{challengeId}")
    public ResponseEntity<Void> remit(@PathVariable Long challengeId, @RequestBody RemittanceDto.RemitRequest requestDto, @AuthenticationPrincipal User user) {
        requestDto.setUserId(user.getId());
        requestDto.setChallengeId(challengeId);
        remittanceService.remit(requestDto, Optional.empty());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "절약하기[테스트용]", description = "계좌 내역 조회를 생략하고, 절약금 1000원을 저장합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/remittance/test/challenges/{challengeId}")
    public ResponseEntity<Void> remitForTest(@PathVariable Long challengeId, @RequestBody RemittanceDto.RemitRequest requestDto, @AuthenticationPrincipal User user) {
        requestDto.setUserId(user.getId());
        requestDto.setChallengeId(challengeId);
        requestDto.setAmount(11L);
        remittanceService.remitTest(requestDto, Optional.of(LocalDateTime.of(2019, 5, 1, 1, 1, 1)));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "절약 내역 조회", description = "조회할 연월을 yyyyMM 형식으로 입력받아 해당 달에 기록된 절약 내역을 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/remittance")
    public ResponseEntity<List<RemittanceDto.DetailsResponse>> getMonthlyRemitDetails(@Schema(example = "202304") String yearMonthStr, @AuthenticationPrincipal User user) {
        List<RemittanceDto.DetailsResponse> responseDto = remittanceService.getRemittanceDetails(user.getId(), yearMonthStr);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "사용자의 절약 상태 요약", description = "메인화면에 사용되는 기능입니다. 총 절약금액, 현재 월의 절약 횟수, 챌린지별 등수를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/remittance/status")
    public ResponseEntity<RemittanceDto.StatusResponse> getRemittanceStatus(@AuthenticationPrincipal User user) {
        RemittanceDto.StatusResponse responseDto = remittanceService.getBriefStatus(user, Optional.empty());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "지난 달 총 절약 금액 조회", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/remittance/prev-month")
    public ResponseEntity<Long> getPreviousSavingAmount(@AuthenticationPrincipal User user) {
        long response = remittanceService.getPreviousSavingAmount(user.getId(), Optional.empty());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
