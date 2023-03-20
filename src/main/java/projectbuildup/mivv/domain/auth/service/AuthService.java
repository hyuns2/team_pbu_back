package projectbuildup.mivv.domain.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.auth.dto.AuthDto;
import projectbuildup.mivv.domain.auth.repository.IdentityVerificationRepository;
import projectbuildup.mivv.domain.auth.repository.TokenRepository;
import projectbuildup.mivv.domain.member.entity.IdentityVerification;
import projectbuildup.mivv.domain.member.entity.User;
import projectbuildup.mivv.domain.member.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.*;
import projectbuildup.mivv.global.security.jwt.JwtProvider;
import projectbuildup.mivv.global.security.jwt.TokenDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IdentityVerificationRepository identityVerificationRepository;
    private final JwtProvider jwtProvider;
    private final TokenRepository tokenRepository;

    /**
     * 회원 가입합니다.
     *
     * @param requestDto 회원가입에 필요한 사용자 정보
     */
    public void signup(AuthDto.SignupRequest requestDto) {
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = requestDto.toEntity(encodedPassword);
        userRepository.save(user);
    }

    /**
     * 로그인합니다.
     *
     * @param requestDto 본인인증 코드, 로그인 비밀번호
     * @return 서비스 토큰 (액세스 토큰, 리프레시 토큰)
     */
    public TokenDto login(AuthDto.LoginRequest requestDto) {
        IdentityVerification identityVerification = identityVerificationRepository.findByCode(requestDto.getVerificationCode()).orElseThrow(CVerificationNotFoundException::new);
        User user = userRepository.findByIdentityVerification(identityVerification).orElseThrow(CUserNotFoundException::new);
        if (passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            TokenDto tokenDto = jwtProvider.generateToken(user);
            tokenRepository.saveRefreshToken(tokenDto.getRefreshToken(), user.getId());
            return tokenDto;
        }
        throw new CWrongPasswordException();
    }
}
