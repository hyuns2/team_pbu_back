package projectbuildup.mivv.domain.worthyConsumption.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
@Repository
public interface WorthyConsumptionRepository extends JpaRepository<WorthyConsumption, Long> {
}
