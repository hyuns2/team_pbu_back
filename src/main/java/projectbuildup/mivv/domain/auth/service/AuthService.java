package projectbuildup.mivv.domain.auth.service;

import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.auth.dto.AuthDto;
import projectbuildup.mivv.domain.auth.repository.IdentityVerificationRepository;
import projectbuildup.mivv.domain.auth.repository.TokenRepository;
import projectbuildup.mivv.domain.couponIssuance.repository.CouponIssuanceRepository;
import projectbuildup.mivv.domain.inquiry.repository.InquiryRepository;
import projectbuildup.mivv.domain.participation.repository.ParticipationRepository;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.*;
import projectbuildup.mivv.global.security.jwt.JwtProvider;
import projectbuildup.mivv.global.security.jwt.JwtValidator;
import projectbuildup.mivv.global.security.jwt.TokenDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IdentityVerificationRepository identityVerificationRepository;
    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final TokenRepository tokenRepository;
    private final CouponIssuanceRepository issuanceRepository;
    private final InquiryRepository inquiryRepository;
    private final ParticipationRepository participationRepository;

    /**
     * 회원 가입합니다.
     * 이미 해당 개인정보로 가입된 계정이 존재할 경우, CUserExistException 을 던집니다.
     *
     * @param requestDto 회원가입에 필요한 사용자 정보
     */
    @Transactional
    public void signup(AuthDto.SignupRequest requestDto) {
        IdentityVerification identityVerification = identityVerificationRepository.findByCode(requestDto.getVerificationCode()).orElseThrow(CVerificationNotFoundException::new);
        if (identityVerification.getUser() != null) {
            throw new CUserExistException();
        }
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = User.of(requestDto, encodedPassword, identityVerification);
        userRepository.save(user);
    }

    /**
     * 로그인합니다.
     *
     * @param requestDto 본인인증 코드, 로그인 비밀번호
     * @return 서비스 토큰 (액세스 토큰, 리프레시 토큰)
     */
    @Transactional
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

    /**
     * 로그아웃합니다.
     * 어세스토큰을 Block처리하고, 리프레시토큰을 레디스에서 제거합니다.
     *
     * @param requestDto 어세스토큰, 리프레시토큰
     */
    @Transactional
    public void logout(AuthDto.UnlinkRequestDto requestDto) {
//        tokenRepository.saveBlockedToken(requestDto.getAccessToken());
        tokenRepository.deleteRefreshToken(requestDto.getRefreshToken());
    }

    /**
     * 회원을 탈퇴합니다.
     * DB에서 회원을 삭제하고, 로그아웃 로직을 실행합니다.
     *
     * @param requestDto 어세스토큰, 리프레시토큰
     */
    @Transactional
    public void withdraw(AuthDto.UnlinkRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(CUserNotFoundException::new);
        deleteRelatedData(user);
        userRepository.delete(user);
        logout(requestDto);
    }

    /**
     * 사용자와 연관된 데이터를 제거합니다.
     *
     * @param user 사용자
     */
    @Transactional
    private void deleteRelatedData(User user) {
        issuanceRepository.deleteAllByUser(user);
        inquiryRepository.deleteAllByUser(user);
        participationRepository.deleteAllByUser(user);
    }

    /**
     * 어세스토큰과 리프레시토큰을 재발급합니다.
     * 리프레시토큰도 만료되면 리이슈에 실패합니다.
     *
     * @param requestDto 어세스토큰, 리프레시토큰
     * @return 재발급한 어세스토큰, 리프레시토큰
     */
    @Transactional
    public TokenDto reissue(AuthDto.ReissueRequest requestDto) {
        isReissueAvailable(requestDto.getAccessToken(), requestDto.getRefreshToken());
        Claims claims = jwtValidator.validateAccessToken(requestDto.getAccessToken());
        User user = (User) jwtProvider.getAuthentication(claims).getPrincipal();
        TokenDto tokenForm = jwtProvider.generateToken(user);
        tokenRepository.saveRefreshToken(tokenForm.getRefreshToken(), user.getId());
        tokenRepository.deleteRefreshToken(requestDto.getRefreshToken());
        return tokenForm;
    }

    private void isReissueAvailable(String accessToken, String refreshToken) {
        String subjectInAccessToken = jwtValidator.validateAccessTokenForReissue(accessToken).getSubject();
        String subjectInRefreshToken = jwtValidator.validateRefreshTokenForReissue(refreshToken).getSubject();
        String userId = tokenRepository.findByRefreshToken(refreshToken).orElseThrow(CReissueFailedException::new);
        if (!(subjectInAccessToken.equals(subjectInRefreshToken) && subjectInAccessToken.equals(userId))) {
            throw new CReissueFailedException();
        }
    }

    public boolean checkNickname(String nickname) {
        return userRepository.findByNickname(nickname).isEmpty();
    }
}
