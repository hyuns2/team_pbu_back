package projectbuildup.mivv.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.auth.repository.IdentityVerificationRepository;

import java.security.cert.Certificate;

@Service
@RequiredArgsConstructor
public class IdentityVerificationService {

    private final IdentityVerificationRepository identityVerificationRepository;
    private final CertificationSystem certificationSystem;
    public void verifyIdentity(String key){

    }
}
