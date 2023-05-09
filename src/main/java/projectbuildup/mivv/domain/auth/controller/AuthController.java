package projectbuildup.mivv.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.auth.dto.AuthDto;
import projectbuildup.mivv.domain.auth.dto.VerificationResponseDto;
import projectbuildup.mivv.domain.auth.service.AuthService;
import projectbuildup.mivv.domain.auth.service.IdentityVerificationService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

import projectbuildup.mivv.global.security.jwt.TokenDto;

@Tag(name = "[1.Authentication]", description = "인증과 관련된 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    private final IdentityVerificationService identityVerificationService;

    @Operation(summary = "본인인증합니다.", description = "본인인증 API 호출 결과로 받은 key를 이용해 본인인증을 수행합니다. 반환받은 verificationCode는 로그인 및 회원가입 시 사용됩니다. " +
            "만약 이미 회원가입 된 계정이 있다면, isNewUser = true를 반환합니다.")
    @PostMapping("/auth/certify")
    @PreAuthorize("permitAll()")
    public ResponseEntity<VerificationResponseDto> certify(@RequestBody @Valid AuthDto.CertifyRequest requestDto) {
        VerificationResponseDto responseDto = identityVerificationService.verifyIdentity(requestDto.getKey());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "회원가입합니다.", description = "verficiationCode는 본인인증 결과로 반환되는 코드입니다. ")
    @PostMapping("/auth/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> signup(@RequestBody @Valid AuthDto.SignupRequest requestDto) {
        authService.signup(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "로그인합니다.", description = "")
    @PostMapping("/auth/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid AuthDto.LoginRequest requestDto) {
        TokenDto responseDto = authService.login(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "로그아웃합니다.", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @Parameter(name = Header.REFRESH_TOKEN, description = "리프레시토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.REFRESH)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(@RequestHeader(Header.ACCESS_TOKEN) String accessToken, @RequestHeader(Header.REFRESH_TOKEN) String refreshToken, @AuthenticationPrincipal User user) {
        AuthDto.UnlinkRequestDto requestDto = new AuthDto.UnlinkRequestDto(user.getId(), accessToken, refreshToken);
        authService.logout(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "회원 탈퇴합니다.", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @Parameter(name = Header.REFRESH_TOKEN, description = "리프레시토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.REFRESH)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/auth/withdraw")
    public ResponseEntity<Void> withdraw(@RequestHeader(Header.ACCESS_TOKEN) String accessToken, @RequestHeader(Header.REFRESH_TOKEN) String refreshToken, @AuthenticationPrincipal User user) {
        AuthDto.UnlinkRequestDto requestDto = new AuthDto.UnlinkRequestDto(user.getId(),accessToken, refreshToken);
        authService.withdraw(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "어세스토큰과 리프레시토큰을 재발급합니다.", description = "만료된 어세스토큰으로만 리이슈를 호출할 수 있습니다.<br>" +
            "리프레시토큰도 만료된 경우, 리이슈에 실패합니다.")
    @PostMapping("/auth/reissue")
    @PreAuthorize("permitAll()")
    public ResponseEntity<TokenDto> reissue(@RequestBody @Valid AuthDto.ReissueRequest requestDto) {
        TokenDto responseDto = authService.reissue(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
