package projectbuildup.gasomann.domain.auth.service.component;

import projectbuildup.gasomann.domain.user.entity.IdentityVerification;

public interface CertificationSystem {

    IdentityVerification certify(String txId, String authUrl, String token);
}
