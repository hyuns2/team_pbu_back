package projectbuildup.mivv.domain.archiving.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectbuildup.mivv.domain.archiving.entity.Card;
import projectbuildup.mivv.domain.archiving.entity.CardType;
import projectbuildup.mivv.domain.archiving.entity.UserCard;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.List;

@Repository
public interface CardRepository<T extends Card> extends JpaRepository<T, Long>, CustomCardRepository {

}
