package projectbuildup.mivv.domain.saving_count.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.saving_count.entity.SavingCount;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.Optional;

public interface SavingCountJpaRepository extends JpaRepository<SavingCount, Long>{
}
