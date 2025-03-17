package projectbuildup.gasomann.domain.archiving.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import projectbuildup.gasomann.domain.archiving.entity.Card;
import projectbuildup.gasomann.domain.archiving.entity.CardType;
import projectbuildup.gasomann.domain.user.entity.User;

import java.util.List;

import static projectbuildup.gasomann.domain.archiving.entity.QCard.card;
import static projectbuildup.gasomann.domain.archiving.entity.QUserCard.userCard;

@RequiredArgsConstructor
public class CustomCardRepositoryImpl implements CustomCardRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Card> findAllByTypeCond(CardType cardType) {
        return queryFactory
                .selectFrom(card)
                .where(cardTypeEq(cardType))
                .fetch();
    }

    @Override
    public List<Tuple> findAllWithUserCardsByUserAndTypeCond(User user, CardType cardType) {
        return queryFactory
                .select(card, userCard)
                .from(card)
                .leftJoin(userCard).fetchJoin()
                .on(userCard.card.eq(card).and(userCard.user.eq(user)))
                .where(cardTypeEq(cardType))
                .fetch();
    }

    @Override
    public List<Card> findNotHavingCardsByUserAndTypeCond(User user, CardType cardType) {
        return queryFactory
                .selectFrom(card)
                .leftJoin(userCard).fetchJoin()
                .on(userCard.card.eq(card).and(userCard.user.eq(user)))
                .where(cardTypeEq(cardType), userCard.user.isNull())
                .fetch();
    }

    private BooleanExpression cardTypeEq(CardType cardType) {
        return cardType != null ? card.cardType.eq(cardType) : null;
    }
}
