package projectbuildup.mivv.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.mivv.domain.member.entity.IdentityVerification;

import java.util.Optional;

public interface IdentityVerificationRepository extends JpaRepository<IdentityVerification, Long> {
    Optional<IdentityVerification> findByCode(String code);
}
