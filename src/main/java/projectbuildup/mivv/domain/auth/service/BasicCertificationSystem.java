package projectbuildup.mivv.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;

@Component
@RequiredArgsConstructor
public class BasicCertificationSystem implements CertificationSystem{
    @Override
    public IdentityVerification certify(String key) {
        return IdentityVerification.generateDummyVerification();
    }
}
