package projectbuildup.mivv.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.auth.repository.IdentityVerificationRepository;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;

@Service
@RequiredArgsConstructor
public class IdentityVerificationService {

    private final IdentityVerificationRepository identityVerificationRepository;
    private final CertificationSystem certificationSystem;

    /**
     * 각 본인인증 API 호출에 필요한 key를 바탕으로 인증 객체를 생성하고, DB에 저장합니다.
     *
     * @param key API 호출 key
     * @return 본인인증 객체의 고유코드
     */
    public String verifyIdentity(String key) {
        IdentityVerification verification = certificationSystem.certify(key);
        identityVerificationRepository.save(verification);
        return verification.getCode();
    }
}
