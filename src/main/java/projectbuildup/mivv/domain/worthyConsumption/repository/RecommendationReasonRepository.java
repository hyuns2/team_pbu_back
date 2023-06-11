package projectbuildup.mivv.domain.worthyConsumption.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.mivv.domain.shorts.entity.Shorts;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.worthyConsumption.entity.RecommendationReason;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;

public interface RecommendationReasonRepository extends JpaRepository<RecommendationReason, Long> {
    @Transactional
    void deleteAllByWorthyConsumption(WorthyConsumption worthyConsumption);
}
