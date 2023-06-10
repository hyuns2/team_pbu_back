package projectbuildup.mivv.domain.auth.service.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;
import projectbuildup.mivv.global.error.exception.CInternalServerException;

import java.nio.charset.StandardCharsets;

@Component
@Primary
@Slf4j
@RequiredArgsConstructor
public class KgCertificationSystem implements CertificationSystem {
    private final KgClient kgClient;

    /**
     * KG 본인인증을 수행하고, 개인정보를 복호화하여 IdentityVerification 객체를 생성합니다.
     *
     * @param txId 트랜잭션 아이디 (프론트 측 전달)
     * @param authUrl 요청 URL (프론트 측 전달)
     * @return IdentityVerification
     */
    @Override
    public IdentityVerification certify(String txId, String authUrl, String token) {
        try {
            KgClient.ResponseBody data = kgClient.request(txId, authUrl);
            String name = CbcDecoder.decrypt(data.getUserName().getBytes(StandardCharsets.UTF_8), token);
            String birthday = CbcDecoder.decrypt(data.getUserBirthday().getBytes(), token);
            String phone = CbcDecoder.decrypt(data.getUserPhone().getBytes(), token);
            return IdentityVerification.generateVerification(name, birthday, phone);
        }catch (Exception e){
            e.printStackTrace();
            throw new CInternalServerException();
        }
    }
}
