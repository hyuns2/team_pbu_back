package projectbuildup.gasomann.domain.worthyConsumption.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.gasomann.domain.worthyConsumption.entity.RecommendationReason;
import projectbuildup.gasomann.domain.worthyConsumption.entity.WorthyConsumption;

public interface RecommendationReasonRepository extends JpaRepository<RecommendationReason, Long> {
    @Transactional
    void deleteAllByWorthyConsumption(WorthyConsumption worthyConsumption);
}
