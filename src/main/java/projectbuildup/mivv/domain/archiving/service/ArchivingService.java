package projectbuildup.mivv.domain.archiving.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
import projectbuildup.mivv.domain.archiving.entity.CardEntity;
import projectbuildup.mivv.domain.archiving.entity.NumericalConditionCardEntity;
import projectbuildup.mivv.domain.archiving.entity.UserCardEntity;
import projectbuildup.mivv.domain.archiving.repository.NumericalConditionCardRepository;
import projectbuildup.mivv.domain.archiving.repository.UserCardRepository;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.error.exception.CCardNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArchivingService {

    private final NumericalConditionCardRepository repo;
    private final UserCardRepository userCardRepo;
    private final RemittanceRepository remittanceRepo;

    public void createNumericalConditionCard(final ArchivingDto.createNumericalConditionCardRequestDto dto) {

        NumericalConditionCardEntity entity = ArchivingDto.createNumericalConditionCardRequestDto.toEntity(dto);

        repo.save(entity);

    }

    public void updateNumericalConditionCard(final Long id, final ArchivingDto.updateNumericalConditionCardRequestDto dto) {

        Optional<NumericalConditionCardEntity> target = repo.findById(id);
        if (target.isEmpty()) {
            throw new CCardNotFoundException();
        }

        NumericalConditionCardEntity result = target.get();
        result.updateCard(dto);

        repo.save(result);

    }

    public void deleteNumericalConditionCard(final Long id) {

        Optional<NumericalConditionCardEntity> target = repo.findById(id);
        if (target.isEmpty()) {
            throw new CCardNotFoundException();
        }

        NumericalConditionCardEntity result = target.get();
        repo.delete(result);

    }

    public ArchivingDto.NumericalConditionCardResponseDto retrieveNumericalConditionCard(final Long id) {

        Optional<NumericalConditionCardEntity> target = repo.findById(id);
        if (target.isEmpty()) {
            throw new CCardNotFoundException();
        }

        NumericalConditionCardEntity result = target.get();
        return new ArchivingDto.NumericalConditionCardResponseDto(result);

    }

    public List<ArchivingDto.NumericalConditionCardResponseDto> retrieveNumericalConditionCards() {

        List<NumericalConditionCardEntity> result = repo.findAll();

        return result.stream().map(ArchivingDto.NumericalConditionCardResponseDto::new).collect(Collectors.toList());

    }

    public List<ArchivingDto.UserCardResponseDto> retrieveUserCards(final User user) {

        List<UserCardEntity> result = userCardRepo.findUserCardEntitiesByUser(user);

        return result.stream().map(ArchivingDto.UserCardResponseDto::new).collect(Collectors.toList());

    }

    public void assignNumericalConditionCards(final User user) {

        List<UserCardEntity> alreadyExistings = userCardRepo.findUserCardEntitiesByUser(user);

        List<NumericalConditionCardEntity> allCards = repo.findAll();

        for (UserCardEntity element: alreadyExistings) {
            allCards.remove(element.getNumericalConditionCardEntity());
        }

        for (NumericalConditionCardEntity element: allCards) {
            Integer charge = element.getCharge();
            Integer count = element.getCount();
            Integer term = element.getTerm();

            if (term == 0) {
                Integer chargeSum = remittanceRepo.findChargeSum(user);
                Integer countSum = remittanceRepo.findCountSum(user);

                if (chargeSum == null) {
                    chargeSum = 0;
                }

                if (chargeSum >= charge && countSum >= count) {
                    userCardRepo.save(new UserCardEntity(user, element, LocalDate.now()));
                }
            }
            else {
                Integer chargeSum = remittanceRepo.findChargeSumBetweenTerm(user, LocalDateTime.now().minusDays(term), LocalDateTime.now());
                Integer countSum = remittanceRepo.findCountSumBetweenTerm(user, LocalDateTime.now().minusDays(term), LocalDateTime.now());

                if (chargeSum == null) {
                    chargeSum = 0;
                }

                if (chargeSum >= charge && countSum >= count) {
                    userCardRepo.save(new UserCardEntity(user, element, LocalDate.now()));
                }
            }
        }

    }

}
