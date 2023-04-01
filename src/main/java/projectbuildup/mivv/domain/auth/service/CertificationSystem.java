package projectbuildup.mivv.domain.auth.service;

import projectbuildup.mivv.domain.member.entity.IdentityVerification;

public interface CertificationSystem {
    IdentityVerification certify(String key);
}
