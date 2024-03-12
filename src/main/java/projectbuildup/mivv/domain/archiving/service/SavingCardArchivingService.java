package projectbuildup.mivv.domain.archiving.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
import projectbuildup.mivv.domain.archiving.entity.CardType;
import projectbuildup.mivv.domain.archiving.entity.SavingCardEntity;
import projectbuildup.mivv.domain.archiving.entity.UserCardEntity;
import projectbuildup.mivv.domain.archiving.repository.CardRepository;
import projectbuildup.mivv.domain.archiving.repository.UserCardRepository;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.imageStore.Image;
import projectbuildup.mivv.global.common.imageStore.ImageType;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
import projectbuildup.mivv.global.error.exception.CCardNotFoundException;
import projectbuildup.mivv.global.error.exception.CCardTypeNotMatchException;
import projectbuildup.mivv.global.error.exception.CInvalidCardConditionException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SavingCardArchivingService {

    private final CardRepository<SavingCardEntity> cardRepo;
    private final UserCardRepository userCardRepo;
    private final RemittanceRepository remittanceRepo;
    private final ImageUploader imageUploader;

    /**
     * 절약 카드 생성
     *
     * @param dto 카드 정보, 발급 조건
     * @throws CInvalidCardConditionException 카드 조건이 없을 시
     */
    public void createSavingCard(final ArchivingDto.createOrUpdateSavingCardRequestDto dto) throws IOException {
        if (NoAnyConditions(dto)) {
            throw new CInvalidCardConditionException();
        }

        Image image = imageUploader.upload(dto.getImage(), ImageType.CARD);
        SavingCardEntity entity = ArchivingDto.createOrUpdateSavingCardRequestDto.toEntity(dto, image.getImagePath());
        cardRepo.save(entity);
    }

    private boolean NoAnyConditions(ArchivingDto.createOrUpdateSavingCardRequestDto dto) {
        return (dto.getCharge() == 0 && dto.getCount() == 0 && dto.getTerm() == 0);
    }

    /**
     * 절약 카드 수정
     *
     * @param id 카드 Id
     * @param dto 수정하려는 정보
     * @throws CCardNotFoundException 카드 찾기 실패시
     */
    @Transactional
    public void updateSavingCard(final Long id, final ArchivingDto.createOrUpdateSavingCardRequestDto dto) throws IOException {
        if (NoAnyConditions(dto)) {
            throw new CInvalidCardConditionException();
        }

        Optional<SavingCardEntity> target = cardRepo.findById(id);
        if (target.isEmpty()) {
            throw new CCardNotFoundException();
        }
        if (!target.get().getType().equals(CardType.SAVING)) {
            throw new CCardTypeNotMatchException();
        }

        SavingCardEntity result = target.get();
        Image image = imageUploader.upload(dto.getImage(), ImageType.CARD);
        result.updateCard(dto, image.getImagePath());
    }

    /**
     * 절약 카드 발급
     *
     * @param user 유저 정보
     */
    @Transactional
    public void assignCards(final User user) {
        List<SavingCardEntity> checkedCards = getCheckedCards(user);

        assignCards(user, checkedCards);
    }

    private List<SavingCardEntity> getCheckedCards(User user) {
        List<UserCardEntity> alreadyExistings = userCardRepo.findUserCardEntitiesByUser(user);
        List<SavingCardEntity> allCards = cardRepo.findAllByType(CardType.SAVING);

        for (UserCardEntity element: alreadyExistings) {
            allCards.remove(element.getCardEntity());
        }
        return allCards;
    }

    private void assignCards(User user, List<SavingCardEntity> checkedCards) {
        for (SavingCardEntity element: checkedCards) {
            Integer charge = element.getCharge();
            Integer count = element.getCount();
            Integer term = element.getTerm();

            if (term == 0)
                ifNoHaveTerm(user, charge, count, element);
            else
                ifHaveTerm(user, charge, count, term, element);
        }
    }

    private void ifNoHaveTerm(User user, Integer charge, Integer count, SavingCardEntity element) {
        Integer chargeSum = remittanceRepo.findChargeSum(user);
        Integer countSum = remittanceRepo.findCountSum(user);

        if (chargeSum >= charge && countSum >= count)
            userCardRepo.save(new UserCardEntity(user, element, LocalDate.now()));
    }

    private void ifHaveTerm(User user, Integer charge, Integer count, Integer term, SavingCardEntity element) {
        Integer chargeSum = remittanceRepo.findChargeSumBetweenTerm(user, LocalDateTime.now().minusDays(term), LocalDateTime.now());
        Integer countSum = remittanceRepo.findCountSumBetweenTerm(user, LocalDateTime.now().minusDays(term), LocalDateTime.now());

        if (chargeSum >= charge && countSum >= count)
            userCardRepo.save(new UserCardEntity(user, element, LocalDate.now()));
    }

}
