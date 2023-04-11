package projectbuildup.mivv.domain.archiving.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
import projectbuildup.mivv.domain.archiving.entity.NumericalConditionCardEntity;
import projectbuildup.mivv.domain.archiving.repository.NumericalConditionCardRepository;
import projectbuildup.mivv.global.error.exception.CCardNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArchivingService {

    private final NumericalConditionCardRepository repo;

    public void createNumericalConditionCard(final ArchivingDto.NumericalConditionCardRequestDto dto) {

        NumericalConditionCardEntity entity = ArchivingDto.NumericalConditionCardRequestDto.toEntity(dto);

        repo.save(entity);

    }

    public void updateNumericalConditionCard(final Long id, final ArchivingDto.NumericalConditionCardRequestDto dto) {

        Optional<NumericalConditionCardEntity> target = repo.findById(id);
        if (target.isEmpty()) {
            throw new CCardNotFoundException();
        }

        NumericalConditionCardEntity result = target.get();
        result.updateCard(dto);

        repo.save(result);

    }

}
