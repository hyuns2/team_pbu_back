package projectbuildup.mivv.domain.archiving.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projectbuildup.mivv.domain.archiving.entity.CardType;
import projectbuildup.mivv.domain.archiving.entity.UserCard;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.List;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {
    @Query("select u from UserCard u where u.user = ?1 and u.isNew = true")
    List<UserCard> findUserNewCards(User user);

    void deleteAllByUser(User user);
}
