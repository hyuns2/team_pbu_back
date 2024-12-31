package projectbuildup.mivv.domain.archiving.repository;

import com.querydsl.core.Tuple;
import projectbuildup.mivv.domain.archiving.entity.Card;
import projectbuildup.mivv.domain.archiving.entity.CardType;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.List;

public interface CustomCardRepository {
    List<Card> findCardByTypeCond(CardType cardType);
    List<Tuple> findAllCardWithUserCardByTypeCond(User user, CardType cardType);

    List<Card> findNotHavingCardsByUserAndTypeCond(User user, CardType cardType);
}
