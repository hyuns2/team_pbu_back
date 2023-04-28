package projectbuildup.mivv.domain.user.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByIdentityVerification(IdentityVerification identityVerification);
}
