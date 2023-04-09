package projectbuildup.mivv.domain.remittance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.remittance.dto.RemittanceDto;
import projectbuildup.mivv.domain.remittance.service.RemittanceDetailsService;
import projectbuildup.mivv.domain.remittance.service.RemittanceService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

import java.util.List;

@Tag(name = "[4. Remittance]", description = "송금 관련 API입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RemittanceController {
    private final RemittanceService remittanceService;
    private final RemittanceDetailsService detailsService;

    @Operation(summary = "절약하기", description = "절약하기 버튼을 누를 때 실행하는 API입니다. 이후 5분(테스트 환경은 5초)간 내역을 조회하며, 실제 송금이 이루어진 경우에만 절약 정보를 갱신합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/remittance/challenges/{challengeId}")
    public ResponseEntity<Void> remit(@PathVariable Long challengeId, @RequestBody RemittanceDto.RemitRequest requestDto, @AuthenticationPrincipal User user) {
        requestDto.setUserId(user.getId());
        requestDto.setChallengeId(challengeId);
        remittanceService.remit(requestDto, null);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "절약 내역 조회", description = "조회할 연월을 yyyyMM 형식으로 입력받아 해당 달에 기록된 절약 내역을 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/remittance")
    public ResponseEntity<List<RemittanceDto.DetailsResponse>> remit(String yearMonthStr, @AuthenticationPrincipal User user) {
        List<RemittanceDto.DetailsResponse> responseDto = detailsService.getRemittanceDetails(user.getId(), yearMonthStr);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
