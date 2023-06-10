package projectbuildup.mivv.domain.account.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectbuildup.mivv.domain.account.dto.AccountCertifyTransferDto;
import projectbuildup.mivv.domain.account.dto.AccountRegisterDto;
import projectbuildup.mivv.domain.account.service.AccountRegisterService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

@Tag(name = "[1.Authentication]", description = "인증과 관련된 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {
    private final AccountRegisterService accountRegisterService;

    @Operation(summary = "계좌 예금주 일치 확인", description = "")
    @PostMapping("/account/check-owner")
    public ResponseEntity<Boolean> checkAccountOwner(@RequestBody AccountCertifyTransferDto requestDto) {
        boolean response = accountRegisterService.checkAccountOwner(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "1원 인증", description = "인증코드가 반환됩니다. 사용자가 입력한 코드와 대조하여 같다면, 인증에 성공한 것으로 간주합니다. 만약 본인 명의의 계좌가 아닌 경우, 예외가 반환됩니다.")
    @PostMapping("/account/certify-transfer")
    public ResponseEntity<String> certifyTransfer(@RequestBody AccountCertifyTransferDto requestDto) {
        String response = accountRegisterService.certifyTransfer(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "계좌 연동 (은행 ID/비밀번호 이용)", description = "은행 ID와 비밀번호를 사용해 계좌를 연동합니다.")
    @PostMapping("/account/register")
    public ResponseEntity<Void> registerAccount(@RequestBody AccountRegisterDto requestDto) {
        accountRegisterService.registerAccount(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "계좌 초기화", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/account/reset")
    public ResponseEntity<Void> resetAccount(@AuthenticationPrincipal User user) {
        accountRegisterService.resetAccount(user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
