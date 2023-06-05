package projectbuildup.mivv.domain.archiving.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectbuildup.mivv.domain.archiving.entity.CardEntity;
import projectbuildup.mivv.domain.archiving.entity.CardType;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.List;

@Repository
public interface CardRepository<T extends CardEntity> extends JpaRepository<T, Long> {

    @Query("select c, u from CardEntity c left join UserCardEntity u on (c = u.cardEntity and u.user = ?1) where c.type = ?2")
    List<Object[]> findUserGeneralCards(User user, CardType cardType);

    List<CardEntity> findAllByType(CardType type);

}
