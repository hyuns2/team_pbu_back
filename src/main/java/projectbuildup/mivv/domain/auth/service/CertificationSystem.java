package projectbuildup.mivv.domain.auth.service;

import projectbuildup.mivv.domain.user.entity.IdentityVerification;

public interface CertificationSystem {
    IdentityVerification certify(String key);
}
