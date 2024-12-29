package projectbuildup.mivv.domain.archiving.service;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.mivv.domain.archiving.dto.ArchivingResponse;
import projectbuildup.mivv.domain.archiving.entity.*;
import projectbuildup.mivv.domain.archiving.repository.CardRepository;
import projectbuildup.mivv.domain.archiving.repository.UserCardRepository;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.List;

import static projectbuildup.mivv.domain.archiving.entity.QCard.card;
import static projectbuildup.mivv.domain.archiving.entity.QUserCard.userCard;

@RequiredArgsConstructor
@Service
public class ArchivingUserService {
    private final CardRepository<Card> cardRepository;
    private final UserCardRepository userCardRepository;

    /**
     * 사용자의 새로 부여받은 카드들 조회
     *
     * @param user 유저 정보
     * @return List<ArchivingDto.UserCardResponseDto> 유저카드들 정보 전체
     */
    @Transactional(readOnly = true)
    public List<ArchivingResponse.UserCardDto> retrieveNewCards(final User user) {
        List<UserCard> result = userCardRepository.findUserNewCards(user);

        return result.stream().map(ArchivingResponse.UserCardDto::of).toList();
    }

    /**
     * 사용자의 카드 신규여부 갱신
     *
     * @param user 유저 정보
     */
    @Transactional
    public void updateCardToNotNew(final User user) {
        List<UserCard> result = userCardRepository.findUserNewCards(user);

        for (UserCard userCard: result)
            userCard.updateIsNew();
    }

    /**
     * 전체 카드 요약정보 + 카드 소유시 UserCard 정보까지 전체 및 유형별 조회
     *
     * @param user 유저 정보
     * @param cardType 카드 타입
     * @return List<ArchivingDto.CardAndUserCardResponseDto> 전체 카드 요약정보와 소유한 사용자카드 정보
     */
    public List<ArchivingResponse.AllCardSummaryAndUserCardDto> retrieveUserCards(User user, CardType cardType) {
        List<Tuple> userCards = cardRepository.findAllCardWithUserCardByTypeCond(user, cardType);

        return userCards.stream().map((tuple) -> ArchivingResponse.AllCardSummaryAndUserCardDto.of(
                tuple.get(card), tuple.get(userCard))).toList();
    }
}
