package projectbuildup.mivv.domain.archiving.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import projectbuildup.mivv.domain.archiving.entity.Card;
import projectbuildup.mivv.domain.archiving.entity.CardType;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.List;

import static projectbuildup.mivv.domain.archiving.entity.QCard.card;
import static projectbuildup.mivv.domain.archiving.entity.QUserCard.userCard;

@RequiredArgsConstructor
public class CustomCardRepositoryImpl implements CustomCardRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Card> findCardByTypeCond(CardType cardType) {
        return queryFactory
                .selectFrom(card)
                .where(cardTypeEq(cardType))
                .fetch();
    }

    @Override
    public List<Tuple> findAllCardWithUserCardByTypeCond(User user, CardType cardType) {
        return queryFactory
                .select(card, userCard)
                .from(card)
                .leftJoin(card, userCard.card).fetchJoin()
                    .on(userCard.user.eq(user))
                .where(cardTypeEq(cardType))
                .fetch();
    }

    private BooleanExpression cardTypeEq(CardType cardType) {
        return cardType != null ? card.type.eq(cardType) : null;
    }
}
