package projectbuildup.mivv.domain.auth.service.component;

import projectbuildup.mivv.domain.user.entity.IdentityVerification;

public interface CertificationSystem {

    IdentityVerification certify(String txId, String authUrl, String token);
}
