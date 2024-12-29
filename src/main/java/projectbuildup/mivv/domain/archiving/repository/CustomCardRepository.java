package projectbuildup.mivv.domain.archiving.repository;

import com.querydsl.core.Tuple;
import projectbuildup.mivv.domain.archiving.entity.Card;
import projectbuildup.mivv.domain.archiving.entity.CardType;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.List;

public interface CustomCardRepository {
    public List<Card> findCardByTypeCond(CardType cardType);
    public List<Tuple> findAllCardWithUserCardByTypeCond(User user, CardType cardType);
}
