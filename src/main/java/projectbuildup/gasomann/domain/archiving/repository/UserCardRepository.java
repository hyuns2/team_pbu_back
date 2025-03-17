package projectbuildup.gasomann.domain.archiving.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import projectbuildup.gasomann.domain.archiving.entity.Card;
import projectbuildup.gasomann.domain.archiving.entity.UserCard;
import projectbuildup.gasomann.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {
    @EntityGraph(attributePaths = "card")
    List<UserCard> findAllByUserAndIsNew(User user, boolean isNew);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update UserCard u set u.isNew = :isNew where u in :userCards")
    int updateIsNewByUserCards(Boolean isNew, List<UserCard> userCards);

    void deleteAllByUser(User user);

    Optional<UserCard> findByUserIdAndCardId(Long userId, Long cardId);

    @Query("select u.user.id from UserCard u where u.card = :card and u.user in :users")
    List<Long> findUserIdsByCardAndUserIn(Card card, List<User> users);
}
