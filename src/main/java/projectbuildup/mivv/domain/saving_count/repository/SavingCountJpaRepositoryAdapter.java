package projectbuildup.mivv.domain.saving_count.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.saving_count.entity.SavingCount;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.List;
import java.util.Optional;


@Primary
@RequiredArgsConstructor
@Repository
public class SavingCountJpaRepositoryAdapter implements SavingCountRepository{
    private final SavingCountJpaRepository savingCountJpaRepository;

    @Override
    public SavingCount save(SavingCount savingCount) {
        return savingCountJpaRepository.save(savingCount);
    }

    @Override
    public Optional<SavingCount> findByParticipation(Participation participation) {
        return savingCountJpaRepository.findByParticipation(participation);
    }

    @Override
    public List<SavingCount> findAll() {
        return savingCountJpaRepository.findAll();
    }


}
