package projectbuildup.mivv.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.auth.repository.IdentityVerificationRepository;
import projectbuildup.mivv.domain.member.entity.IdentityVerification;
import projectbuildup.mivv.global.error.exception.CInternalServerException;

@Component
@RequiredArgsConstructor
public class BasicCertificationSystem implements CertificationSystem{
    @Override
    public IdentityVerification certify(String key) {
        return IdentityVerification.generateDummyVerification();
    }
}
