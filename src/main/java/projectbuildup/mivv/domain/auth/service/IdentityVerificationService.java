package projectbuildup.mivv.domain.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.account.entity.UserState;
import projectbuildup.mivv.domain.auth.dto.AuthDto;
import projectbuildup.mivv.domain.auth.dto.VerificationResponseDto;
import projectbuildup.mivv.domain.auth.repository.IdentityVerificationRepository;
import projectbuildup.mivv.domain.auth.service.component.CertificationSystem;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdentityVerificationService {

    private final IdentityVerificationRepository identityVerificationRepository;
    private final CertificationSystem certificationSystem;
    private final UserRepository userRepository;

    @Transactional
    public VerificationResponseDto verifyIdentity(AuthDto.CertifyRequest requestDto) {
        IdentityVerification newVerification = certificationSystem.certify(requestDto.getTxId(), requestDto.getAuthUrl(), requestDto.getToken());
        IdentityVerification verification = identityVerificationRepository.findByMobile(newVerification.getMobile()).orElse(newVerification);
        identityVerificationRepository.save(verification);
        return new VerificationResponseDto(verification.getCode(), getUserState(verification).getField(), true);
    }

    /**
     * 회원의 상태를 조회합니다.
     * 회원가입까지만 한 유저 => SIGNUPED
     * 계좌연동까지 한 유저 => REGISTERD
     * 새 유저(혹은 본인인증은 했는데 회원가입을 완료하지 않은 유저) => NEW
     *
     * @param verification 인증 정보
     * @return 회원 상태
     */
    private UserState getUserState(IdentityVerification verification) {
        Optional<User> userOptional = userRepository.findByIdentityVerification(verification);
        if (userOptional.isEmpty()) {
            return UserState.NEW;
        }
        if (userOptional.get().getAccount() == null) {
            return UserState.SIGNUPED;
        }
        return UserState.REGISTERED;
    }
}
