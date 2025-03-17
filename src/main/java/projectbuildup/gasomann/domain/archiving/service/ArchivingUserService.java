package projectbuildup.gasomann.domain.archiving.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.gasomann.domain.archiving.dto.ArchivingResponse;
import projectbuildup.gasomann.domain.archiving.entity.*;
import projectbuildup.gasomann.domain.archiving.repository.CardRepository;
import projectbuildup.gasomann.domain.archiving.repository.UserCardRepository;
import projectbuildup.gasomann.domain.user.entity.User;

import java.util.List;

import static projectbuildup.gasomann.domain.archiving.entity.QCard.card;
import static projectbuildup.gasomann.domain.archiving.entity.QUserCard.userCard;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArchivingUserService {
    private final CardRepository<Card> cardRepository;
    private final UserCardRepository userCardRepository;

    /**
     * 사용자의 새로 부여받은 카드들 조회 & 해당 카드들의 신규여부 갱신 (신규여부 갱신 전의 데이터를 반환)
     */
    @Transactional
    public List<ArchivingResponse.UserCardWithCardSummaryDto> retrieveAndUpdateNewCards(User user) {
        List<UserCard> userCards = userCardRepository.findAllByUserAndIsNew(user, true);
        List<ArchivingResponse.UserCardWithCardSummaryDto> dtos = userCards.stream()
                .map(ArchivingResponse.UserCardWithCardSummaryDto::from).toList();

        userCardRepository.updateIsNewByUserCards(false, userCards);
        return dtos;
    }

    /**
     * 전체 카드 요약정보 + 카드 소유시 UserCard 정보까지 전체 및 유형별 조회
     */
    public List<ArchivingResponse.CardSummaryAndUserCardDto> retrieveUserCards(User user, CardType cardType) {
        return cardRepository.findAllWithUserCardsByUserAndTypeCond(user, cardType).stream()
                .map(tuple -> ArchivingResponse.CardSummaryAndUserCardDto.from(
                        tuple.get(card), tuple.get(userCard))).toList();
    }
}
