package projectbuildup.gasomann.domain.archiving.repository;

import com.querydsl.core.Tuple;
import projectbuildup.gasomann.domain.archiving.entity.Card;
import projectbuildup.gasomann.domain.archiving.entity.CardType;
import projectbuildup.gasomann.domain.user.entity.User;

import java.util.List;

public interface CustomCardRepository {
    List<Card> findAllByTypeCond(CardType cardType);
    List<Tuple> findAllWithUserCardsByUserAndTypeCond(User user, CardType cardType);

    List<Card> findNotHavingCardsByUserAndTypeCond(User user, CardType cardType);
}
