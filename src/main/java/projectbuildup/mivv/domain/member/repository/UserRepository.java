package projectbuildup.mivv.domain.member.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.mivv.domain.member.entity.IdentityVerification;
import projectbuildup.mivv.domain.member.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByIdentityVerification(IdentityVerification identityVerification);
}
