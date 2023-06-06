package projectbuildup.mivv.domain.saving_count.repository;

import org.springframework.stereotype.Repository;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.saving_count.entity.SavingCount;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface SavingCountRepository {
    SavingCount save(SavingCount savingCount);
    List<SavingCount> findAll();
    Optional<SavingCount> findById(Long id);

    void delete(SavingCount savingCount);
}
