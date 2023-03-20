package projectbuildup.mivv.domain.auth.service;

import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.member.entity.IdentityVerification;

@Component
public class BasicCertificationSystem implements CertificationSystem{
    @Override
    public Object certify(String key) {
        return null;
    }

    @Override
    public IdentityVerification generateVerification(Object object) {
        return null;
    }
}
