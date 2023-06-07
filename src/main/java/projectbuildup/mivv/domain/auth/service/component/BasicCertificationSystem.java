package projectbuildup.mivv.domain.auth.service.component;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;

@Component
@Primary
@RequiredArgsConstructor
public class BasicCertificationSystem implements CertificationSystem{
    @Override
    public IdentityVerification certify(String key, String authUrl, String token) {
        return IdentityVerification.generateDummyVerification();
    }
}
