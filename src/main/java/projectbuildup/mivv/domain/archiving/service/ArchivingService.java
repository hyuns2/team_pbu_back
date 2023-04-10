package projectbuildup.mivv.domain.archiving.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
import projectbuildup.mivv.domain.archiving.entity.CardWithConditionEntity;
import projectbuildup.mivv.domain.archiving.repository.CardRepository;

@RequiredArgsConstructor
@Service
public class ArchivingService {

    private final CardRepository repo;

    public void registerNumericalConditionCard(final ArchivingDto.NumericalConditionCardRequestDto dto) {

        CardWithConditionEntity.NumericalConditionCardEntity entity = ArchivingDto.NumericalConditionCardRequestDto.toEntity(dto);

        repo.save(entity);

    }

}
