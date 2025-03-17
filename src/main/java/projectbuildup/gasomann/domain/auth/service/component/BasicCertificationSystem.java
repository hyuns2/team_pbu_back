package projectbuildup.gasomann.domain.auth.service.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import projectbuildup.gasomann.domain.user.entity.IdentityVerification;

@Component
@RequiredArgsConstructor
public class BasicCertificationSystem implements CertificationSystem{
    @Override
    public IdentityVerification certify(String key, String authUrl, String token) {
        return IdentityVerification.generateDummyVerification();
    }
}
