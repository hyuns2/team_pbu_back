package projectbuildup.mivv.domain.auth.service.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;

@Component
@RequiredArgsConstructor
public class KgCertificationSystem implements CertificationSystem {
    private final KgClient kgClient;
    @Override
    public IdentityVerification certify(String txId, String authUrl) {
        KgClient.ResponseBody data = kgClient.request(txId, authUrl);
        return IdentityVerification.generateVerification(data.getUserName(), data.getUserBirthday(), data.getUserPhone());
    }
}
