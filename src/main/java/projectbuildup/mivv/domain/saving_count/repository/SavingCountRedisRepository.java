package projectbuildup.mivv.domain.saving_count.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.saving_count.entity.SavingCount;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.List;
import java.util.Optional;
@Repository
public class SavingCountRedisRepository implements SavingCountRepository {

    @Override
    public SavingCount save(SavingCount savingCount) {
        return null;
    }

    @Override
    public List<SavingCount> findAll() {
        return null;
    }

    @Override
    public Optional<SavingCount> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(SavingCount savingCount) {

    }
}
