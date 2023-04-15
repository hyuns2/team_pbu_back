package projectbuildup.mivv.domain.archiving.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectbuildup.mivv.domain.archiving.entity.NumericalConditionCardEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface NumericalConditionCardRepository extends JpaRepository<NumericalConditionCardEntity, Long> {
    Optional<NumericalConditionCardEntity> findById(Long id);

    List<NumericalConditionCardEntity> findAll();

}
