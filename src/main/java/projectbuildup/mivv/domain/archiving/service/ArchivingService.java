package projectbuildup.mivv.domain.archiving.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
import projectbuildup.mivv.domain.archiving.entity.NumericalConditionCardEntity;
import projectbuildup.mivv.domain.archiving.repository.NumericalConditionCardRepository;
import projectbuildup.mivv.global.error.exception.CCardNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArchivingService {

    private final NumericalConditionCardRepository repo;

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

}
