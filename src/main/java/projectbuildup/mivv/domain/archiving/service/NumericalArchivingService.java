package projectbuildup.mivv.domain.archiving.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
import projectbuildup.mivv.domain.archiving.entity.NumericalConditionCardEntity;
import projectbuildup.mivv.domain.archiving.entity.UserCardEntity;
import projectbuildup.mivv.domain.archiving.repository.CardRepository;
import projectbuildup.mivv.domain.archiving.repository.UserCardRepository;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.imageStore.Image;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
import projectbuildup.mivv.global.error.exception.CCardNotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NumericalArchivingService {

    private final CardRepository cardRepo;
    private final UserCardRepository userCardRepo;
    private final RemittanceRepository remittanceRepo;

    private final ImageUploader imageUploader = new ImageUploader();

    public void createNumericalConditionCard(final ArchivingDto.createNumericalCardRequestDto dto) throws IOException {

        Image image = imageUploader.upload(dto.getImage(), "cards");

        NumericalConditionCardEntity entity = ArchivingDto.createNumericalCardRequestDto.toEntity(dto, image.getImagePath());

        cardRepo.save(entity);

    }

    public void updateNumericalConditionCard(final Long id, final ArchivingDto.updateNumericalCardRequestDto dto) throws IOException {

        Optional<NumericalConditionCardEntity> target = (Optional<NumericalConditionCardEntity>) cardRepo.findById(id);
        if (target.isEmpty()) {
            throw new CCardNotFoundException();
        }

        Image image = imageUploader.upload(dto.getImage(), "cards");

        NumericalConditionCardEntity result = target.get();
        result.updateCard(dto, image.getImagePath());

        cardRepo.save(result);

    }

    public void ifNumericalConditionCardHasNotTerm(User user, Integer charge, Integer count, Integer term, NumericalConditionCardEntity element) {
        Integer chargeSum = remittanceRepo.findChargeSum(user);
        Integer countSum = remittanceRepo.findCountSum(user);

        if (chargeSum == null) {
            chargeSum = 0;
        }

        if (chargeSum >= charge && countSum >= count) {
            userCardRepo.save(new UserCardEntity(user, element, LocalDate.now()));
        }
    }

    public void ifNumericalConditionCardHasTerm(User user, Integer charge, Integer count, Integer term, NumericalConditionCardEntity element) {
        Integer chargeSum = remittanceRepo.findChargeSumBetweenTerm(user, LocalDateTime.now().minusDays(term), LocalDateTime.now());
        Integer countSum = remittanceRepo.findCountSumBetweenTerm(user, LocalDateTime.now().minusDays(term), LocalDateTime.now());

        if (chargeSum == null) {
            chargeSum = 0;
        }

        if (chargeSum >= charge && countSum >= count) {
            userCardRepo.save(new UserCardEntity(user, element, LocalDate.now()));
        }
    }

    public void checkAndAssignNumericalConditionCards(User user, List<NumericalConditionCardEntity> cardsToCheck) {
        for (NumericalConditionCardEntity element: cardsToCheck) {
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

        List<NumericalConditionCardEntity> allCards = (List<NumericalConditionCardEntity>) cardRepo.findAll();

        for (UserCardEntity element: alreadyExistings) {
            allCards.remove(element.getCardEntity());
        }

        List<NumericalConditionCardEntity> cardsToCheck = allCards;

        checkAndAssignNumericalConditionCards(user, cardsToCheck);

    }

}
