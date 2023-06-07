package projectbuildup.mivv.domain.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.auth.dto.AuthDto;
import projectbuildup.mivv.domain.auth.dto.VerificationResponseDto;
import projectbuildup.mivv.domain.auth.repository.IdentityVerificationRepository;
import projectbuildup.mivv.domain.auth.service.component.CertificationSystem;
import projectbuildup.mivv.domain.auth.service.component.KgCertificationSystem;
import projectbuildup.mivv.domain.auth.service.component.KgClient;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdentityVerificationService {

    private final IdentityVerificationRepository identityVerificationRepository;
    private final CertificationSystem certificationSystem;

    /**
     * 각 본인인증 API 호출에 필요한 key를 바탕으로 인증 객체를 생성하고, DB에 저장합니다.
     * 본인인증 결과로 조회한 전화번호로 가입된 회원이 존재하면, 기존 본인인증 객체를 반환합니다.
     * 그렇지 않다면 새로운 본인인증 객체를 반환합니다.
     *
     * @param requestDto 트랜잭션 아이디, 요청 URL
     * @return 본인인증 객체의 고유코드
     */
    @Transactional
    public VerificationResponseDto verifyIdentity(AuthDto.CertifyRequest requestDto) {
        IdentityVerification newVerification = certificationSystem.certify(requestDto.getTxId(), requestDto.getAuthUrl(), null);
        Optional<IdentityVerification> existVerification =  identityVerificationRepository.findByMobile(newVerification.getMobile());
        identityVerificationRepository.save(existVerification.orElse(newVerification));
        return new VerificationResponseDto(existVerification.orElse(newVerification).getCode(), existVerification.isEmpty());
    }

    @Transactional
    public VerificationResponseDto verifyIdentityByKg(AuthDto.CertifyRequest requestDto) {
        IdentityVerification newVerification = new KgCertificationSystem(new KgClient()).certify(requestDto.getTxId(), requestDto.getAuthUrl(), requestDto.getToken());
        Optional<IdentityVerification> existVerification =  identityVerificationRepository.findByMobile(newVerification.getMobile());
        identityVerificationRepository.save(existVerification.orElse(newVerification));
        return new VerificationResponseDto(existVerification.orElse(newVerification).getCode(), existVerification.isEmpty());
    }
}
