package projectbuildup.mivv.domain.archiving.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
import projectbuildup.mivv.domain.archiving.entity.CardType;
import projectbuildup.mivv.domain.archiving.entity.RemittanceConditionCardEntity;
import projectbuildup.mivv.domain.archiving.entity.UserCardEntity;
import projectbuildup.mivv.domain.archiving.repository.CardRepository;
import projectbuildup.mivv.domain.archiving.repository.UserCardRepository;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.imageStore.Image;
import projectbuildup.mivv.global.common.imageStore.ImageType;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
import projectbuildup.mivv.global.error.exception.CCardNotFoundException;
import projectbuildup.mivv.global.error.exception.CInvalidCardConditionException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RemittanceArchivingService {

    private final CardRepository cardRepo;
    private final UserCardRepository userCardRepo;
    private final RemittanceRepository remittanceRepo;

    private final ImageUploader imageUploader;

    /**
     * 절약 카드 생성
     *
     * @param dto 카드 정보, 발급 조건
     * @throws IOException
     * @throws CInvalidCardConditionException 카드 조건이 없을 시
     */
    public void createRemittanceConditionCard(final ArchivingDto.createRemittanceCardRequestDto dto) throws IOException {

        if (dontHaveAnyConditions(dto)) {
            throw new CInvalidCardConditionException();
        }

        Image image = imageUploader.upload(dto.getImage(), ImageType.CARD);

        RemittanceConditionCardEntity entity = ArchivingDto.createRemittanceCardRequestDto.toEntity(dto, image.getImagePath());

        cardRepo.save(entity);

    }

    private boolean dontHaveAnyConditions(ArchivingDto.createRemittanceCardRequestDto dto) {
        return (dto.getCharge() == 0 && dto.getCount() == 0 && dto.getTerm() == 0);
    }

    /**
     * 절약 카드 수정
     *
     * @param id 카드 Id
     * @param dto 수정하려는 정보
     * @throws IOException
     * @throws CCardNotFoundException 카드 찾기 실패시
     */
    @Transactional
    public void updateRemittanceConditionCard(final Long id, final ArchivingDto.updateRemittanceCardRequestDto dto) throws IOException {

        Optional<RemittanceConditionCardEntity> target = (Optional<RemittanceConditionCardEntity>) cardRepo.findById(id);
        if (target.isEmpty()) {
            throw new CCardNotFoundException();
        }

        Image image = imageUploader.upload(dto.getImage(), ImageType.CARD);

        RemittanceConditionCardEntity result = target.get();
        result.updateCard(dto, image.getImagePath());

    }

    /**
     * 절약 카드 발급
     *
     * @param user 유저 정보
     */
    @Transactional
    public void assignRemittanceConditionCards(final User user) {

        List<UserCardEntity> alreadyExistings = userCardRepo.findUserCardEntitiesByUser(user);

        List<RemittanceConditionCardEntity> allCards = (List<RemittanceConditionCardEntity>) cardRepo.findAllByType(CardType.REMITTANCE);

        for (UserCardEntity element: alreadyExistings) {
            allCards.remove(element.getCardEntity());
        }

        List<RemittanceConditionCardEntity> cardsToCheck = allCards;

        checkAndAssignRemittanceConditionCards(user, cardsToCheck);

    }

    private void checkAndAssignRemittanceConditionCards(User user, List<RemittanceConditionCardEntity> cardsToCheck) {
        for (RemittanceConditionCardEntity element: cardsToCheck) {
            Integer charge = element.getCharge();
            Integer count = element.getCount();
            Integer term = element.getTerm();

            if (term == 0) {
                ifRemittanceConditionCardHasNotTerm(user, charge, count, element);
            }
            else {
                ifRemittanceConditionCardHasTerm(user, charge, count, term, element);
            }
        }
    }

    private void ifRemittanceConditionCardHasNotTerm(User user, Integer charge, Integer count, RemittanceConditionCardEntity element) {
        Integer chargeSum = remittanceRepo.findChargeSum(user);
        Integer countSum = remittanceRepo.findCountSum(user);

        if (chargeSum >= charge && countSum >= count) {
            userCardRepo.save(new UserCardEntity(user, element, LocalDate.now()));
        }
    }

    private void ifRemittanceConditionCardHasTerm(User user, Integer charge, Integer count, Integer term, RemittanceConditionCardEntity element) {
        Integer chargeSum = remittanceRepo.findChargeSumBetweenTerm(user, LocalDateTime.now().minusDays(term), LocalDateTime.now());
        Integer countSum = remittanceRepo.findCountSumBetweenTerm(user, LocalDateTime.now().minusDays(term), LocalDateTime.now());

        if (chargeSum >= charge && countSum >= count) {
            userCardRepo.save(new UserCardEntity(user, element, LocalDate.now()));
        }
    }

}
