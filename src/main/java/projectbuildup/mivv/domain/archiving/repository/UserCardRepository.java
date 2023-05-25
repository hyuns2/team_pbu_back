package projectbuildup.mivv.domain.archiving.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import projectbuildup.mivv.domain.archiving.entity.CardEntity;
import projectbuildup.mivv.domain.archiving.entity.UserCardEntity;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.List;

public interface UserCardRepository extends JpaRepository<UserCardEntity, Long> {

    List<UserCardEntity> findUserCardEntitiesByUser(User user);

    @Query("select u from UserCardEntity u where u.user = ?1 and u.is_new = true")
    List<UserCardEntity> findUserNewCards(User user);

    @Modifying(clearAutomatically = true)
    @Query("update UserCardEntity u set u.is_new = false where u.is_new = true and u.user = ?1")
    void updateUserNewCards(User user);

}
