package projectbuildup.mivv.domain.archiving.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.mivv.domain.archiving.entity.UserCardEntity;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.List;

public interface UserCardRepository extends JpaRepository<UserCardEntity, Long> {

    List<UserCardEntity> findUserCardEntitiesByUser(User user);

}
