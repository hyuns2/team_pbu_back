package projectbuildup.mivv.domain.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.auth.dto.BasicAuthDto;
import projectbuildup.mivv.domain.member.entity.Member;
import projectbuildup.mivv.domain.member.repository.MemberJpaRepository;
import projectbuildup.mivv.domain.auth.repository.TokenRedisRepository;
import projectbuildup.mivv.global.error.exception.*;
import projectbuildup.mivv.global.security.jwt.JwtProvider;
import projectbuildup.mivv.global.security.jwt.JwtValidator;
import projectbuildup.mivv.global.security.jwt.TokenForm;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasicAuthService {

    private final MemberJpaRepository memberJpaRepository;

    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final PasswordEncoder passwordEncoder;
    private final TokenRedisRepository tokenRedisRepository;

    public void signup(BasicAuthDto.SignupRequest requestDto) {
        if (memberJpaRepository.findByAccount(requestDto.getAccount()).isPresent()){
            throw new CMemberExistException();
        }
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        Member member = requestDto.toEntity(encodedPassword);
        memberJpaRepository.save(member);
    }

    public BasicAuthDto.LoginResponse login(BasicAuthDto.LoginRequest requestDto) {
        Member member = memberJpaRepository.findByAccount(requestDto.getAccount()).orElseThrow(CMemberNotFoundException::new);
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new CWrongPasswordException();
        }
        TokenForm tokenForm = jwtProvider.generateToken(member);
        tokenRedisRepository.saveRefreshToken(tokenForm.getRefreshToken(), member.getAccount());
        return new BasicAuthDto.LoginResponse(member, tokenForm);
    }

    public void logout(BasicAuthDto.UnlinkRequestDto requestDto) {
        tokenRedisRepository.saveBlockedToken(requestDto.getAccessToken());
        tokenRedisRepository.deleteRefreshToken(requestDto.getRefreshToken());
    }

    public void withdraw(BasicAuthDto.UnlinkRequestDto requestDto) {
        Member member = (Member) jwtProvider.getAuthentication(requestDto.getAccessToken()).getPrincipal();
        memberJpaRepository.deleteById(member.getId());
        logout(requestDto);
    }

    public TokenForm reissue(BasicAuthDto.ReissueRequest requestDto) {
        isReissueAvailable(requestDto.getAccessToken(), requestDto.getRefreshToken());
        Member member = (Member) jwtProvider.getAuthentication(requestDto.getRefreshToken()).getPrincipal();
        TokenForm tokenForm = jwtProvider.generateToken(member);
        tokenRedisRepository.saveRefreshToken(tokenForm.getRefreshToken(), member.getAccount());
        tokenRedisRepository.deleteRefreshToken(requestDto.getRefreshToken());
        return tokenForm;
    }

    private void isReissueAvailable(String accessToken, String refreshToken) {
        String subject = jwtValidator.validateAccessTokenForReissue(accessToken).getSubject();
        jwtValidator.validateRefreshTokenForReissue(refreshToken);
        String memberId = tokenRedisRepository.findByRefreshToken(refreshToken).orElseThrow(CReissueFailedException::new);
        if (!subject.equals(memberId)) {
            throw new CReissueFailedException();
        }
    }
}
