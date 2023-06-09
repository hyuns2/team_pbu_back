package projectbuildup.mivv.domain.likes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.mivv.domain.likes.entity.LikesShorts;
import projectbuildup.mivv.domain.likes.entity.LikesWorthyConsumption;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;

import java.util.List;
import java.util.Optional;

public interface LikesWorthyConsumptionRepository extends JpaRepository<LikesWorthyConsumption, Long> {
    void deleteLikesWorthyConsumptionByUserAndWorthyConsumption(User user, WorthyConsumption worthyConsumption);
    List<LikesWorthyConsumption> findAllByUser(User user);
    Optional<LikesWorthyConsumption> findByUserAndWorthyConsumption(User user, WorthyConsumption worthyConsumption);

    void deleteAllByUser(User user);
    void deleteAllByWorthyConsumption(WorthyConsumption worthyConsumption);
}
