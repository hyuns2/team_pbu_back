package projectbuildup.mivv.domain.archiving.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
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

    public void createRemittanceConditionCard(final ArchivingDto.createRemittanceCardRequestDto dto) throws IOException {

        // 조건이 하나도 안주어져 있는 경우
        if (dto.getCharge() == 0 && dto.getCount() == 0 && dto.getTerm() == 0) {
            throw new CInvalidCardConditionException();
        }

        Image image = imageUploader.upload(dto.getImage(), ImageType.CARD);

        RemittanceConditionCardEntity entity = ArchivingDto.createRemittanceCardRequestDto.toEntity(dto, image.getImagePath());

        cardRepo.save(entity);

    }

    public void updateRemittanceConditionCard(final Long id, final ArchivingDto.updateRemittanceCardRequestDto dto) throws IOException {

        Optional<RemittanceConditionCardEntity> target = (Optional<RemittanceConditionCardEntity>) cardRepo.findById(id);
        if (target.isEmpty()) {
            throw new CCardNotFoundException();
        }

        Image image = imageUploader.upload(dto.getImage(), ImageType.CARD);

        RemittanceConditionCardEntity result = target.get();
        result.updateCard(dto, image.getImagePath());

        cardRepo.save(result);

    }

    private void ifNumericalConditionCardHasNotTerm(User user, Integer charge, Integer count, Integer term, RemittanceConditionCardEntity element) {
        Integer chargeSum = remittanceRepo.findChargeSum(user);
        Integer countSum = remittanceRepo.findCountSum(user);

        if (chargeSum >= charge && countSum >= count) {
            userCardRepo.save(new UserCardEntity(user, element, LocalDate.now()));
        }
    }

    private void ifNumericalConditionCardHasTerm(User user, Integer charge, Integer count, Integer term, RemittanceConditionCardEntity element) {
        Integer chargeSum = remittanceRepo.findChargeSumBetweenTerm(user, LocalDateTime.now().minusDays(term), LocalDateTime.now());
        Integer countSum = remittanceRepo.findCountSumBetweenTerm(user, LocalDateTime.now().minusDays(term), LocalDateTime.now());

        if (chargeSum >= charge && countSum >= count) {
            userCardRepo.save(new UserCardEntity(user, element, LocalDate.now()));
        }
    }

    private void checkAndAssignNumericalConditionCards(User user, List<RemittanceConditionCardEntity> cardsToCheck) {
        for (RemittanceConditionCardEntity element: cardsToCheck) {
            Integer charge = element.getCharge();
            Integer count = element.getCount();
            Integer term = element.getTerm();

            if (term == 0) {
                ifNumericalConditionCardHasNotTerm(user, charge, count, term, element);
            }
            else {
                ifNumericalConditionCardHasTerm(user, charge, count, term, element);
            }
        }
    }

    @Transactional
    public void assignNumericalConditionCards(final User user) {

        List<UserCardEntity> alreadyExistings = userCardRepo.findUserCardEntitiesByUser(user);

        List<RemittanceConditionCardEntity> allCards = (List<RemittanceConditionCardEntity>) cardRepo.findAll();

        for (UserCardEntity element: alreadyExistings) {
            allCards.remove(element.getCardEntity());
        }

        List<RemittanceConditionCardEntity> cardsToCheck = allCards;

        checkAndAssignNumericalConditionCards(user, cardsToCheck);

    }

}
