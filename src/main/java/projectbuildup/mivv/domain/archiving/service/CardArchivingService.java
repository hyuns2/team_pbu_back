package projectbuildup.mivv.domain.archiving.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
import projectbuildup.mivv.domain.archiving.entity.CardEntity;
import projectbuildup.mivv.domain.archiving.entity.CardType;
import projectbuildup.mivv.domain.archiving.entity.UserCardEntity;
import projectbuildup.mivv.domain.archiving.repository.CardRepository;
import projectbuildup.mivv.domain.archiving.repository.UserCardRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.error.exception.CCardNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CardArchivingService {

    private final CardRepository<CardEntity> cardRepo;
    private final UserCardRepository userCardRepo;

    /**
     * 관리자가 카드 삭제
     *
     * @param id 카드 Id
     * @throws CCardNotFoundException 카드 찾기 실패시
     */
    @Transactional
    public void deleteCard(final Long id) {
        Optional<CardEntity> target = cardRepo.findById(id);
        if (target.isEmpty())
            throw new CCardNotFoundException();

        CardEntity result = target.get();
        cardRepo.delete(result);
    }

    /**
     * 카드 단건 조회
     *
     * @param id 카드 Id
     * @return ArchivingDto.CardResponseDto 카드 정보 전체
     * @throws CCardNotFoundException 카드 찾기 실패시
     */
    public ArchivingDto.CardResponseDto retrieveCard(final Long id) {
        Optional<CardEntity> target = cardRepo.findById(id);
        if (target.isEmpty())
            throw new CCardNotFoundException();

        CardEntity result = target.get();
        return new ArchivingDto.CardResponseDto(result);
    }

    /**
     * 카드 전체 조회
     *
     * @return List<ArchivingDto.CardResponseDto> 카드들의 정보 전체
     */
    public List<ArchivingDto.CardResponseDto> retrieveCards() {
        List<CardEntity> result = cardRepo.findAll();

        return result.stream().map(ArchivingDto.CardResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 사용자의 새로 부여받은 카드들 조회
     *
     * @param user 유저 정보
     * @return List<ArchivingDto.UserCardResponseDto> 유저카드들 정보 전체
     */
    public List<ArchivingDto.UserCardResponseDto> retrieveNewUserCards(final User user) {
        List<UserCardEntity> result = userCardRepo.findUserNewCards(user);

        return result.stream().map(ArchivingDto.UserCardResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 사용자의 카드 신규여부 갱신
     *
     * @param user 유저 정보
     */
    @Transactional
    public void updateCardToNotNew(final User user) {
        List<UserCardEntity> result = userCardRepo.findUserNewCards(user);

        for (UserCardEntity entity: result)
            entity.updateIsNew();
    }

    /**
     * 사용자가 보유한 카드라면 그 정보까지 카드 전체 정보를 반환
     *
     * @param user 유저 정보
     * @param cardType 카드 타입
     * @return List<ArchivingDto.CardAndUserCardResponseDto> 카드와 사용자카드 정보 전체
     */
    public List<ArchivingDto.CardAndUserCardResponseDto> retrieveUserCards(final User user, final CardType cardType) {
        List<UserCardEntity> userCards = cardRepo.findUserCards(user, cardType);
        List<ArchivingDto.CardAndUserCardResponseDto> dtos = new ArrayList<ArchivingDto.CardAndUserCardResponseDto>();

        for (UserCardEntity userCard: userCards)
            dtos.add(new ArchivingDto.CardAndUserCardResponseDto(userCard.getCardEntity(), userCard));

        return dtos;
    }

}
