package projectbuildup.mivv.domain.user.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByIdentityVerification(IdentityVerification identityVerification);

    @Query("select u from User u left join u.identityVerification i where i.name = ?1 and i.mobile = ?2 and u.deletedAt IS NULL")
    Optional<User> findByNameAndMobile(String name, String mobile);

    Optional<User> findByNickname(String nickname);
}
