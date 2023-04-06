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
import projectbuildup.mivv.domain.account.dto.IdPasswordBasedRegisterDto;
import projectbuildup.mivv.domain.account.service.AccountRegisterService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

@Tag(name="[1-2.Account]", description = "계좌 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {
    private final AccountRegisterService accountRegisterService;

    @Operation(summary = "계좌 연동 (은행 ID/비밀번호 이용)", description = "은행 ID와 비밀번호를 사용해 계좌를 연동합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/account/register")
    public ResponseEntity<Void> registerAccount(@RequestBody IdPasswordBasedRegisterDto requestDto, @AuthenticationPrincipal User user) {
        accountRegisterService.registerAccount(requestDto, user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
