package projectbuildup.mivv.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.auth.dto.AuthDto;
import projectbuildup.mivv.domain.auth.service.AuthService;
import projectbuildup.mivv.global.security.jwt.TokenDto;

@Tag(name = "[1-1.Authentication]", description = "인증과 관련된 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입합니다.", description = "")
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
}
