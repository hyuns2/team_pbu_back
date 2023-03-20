package projectbuildup.mivv.domain.auth.service;

import projectbuildup.mivv.domain.member.entity.IdentityVerification;

public interface CertificationSystem {
    Object certify(String key);

    IdentityVerification generateVerification(Object object);
}
