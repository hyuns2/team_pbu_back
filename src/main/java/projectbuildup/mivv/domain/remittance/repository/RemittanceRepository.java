package projectbuildup.mivv.domain.remittance.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.List;

public interface RemittanceRepository extends JpaRepository<Remittance, Long> {

    List<Remittance> findAllByChallengeAndUser(Challenge challenge, User user);

    List<Remittance> findAllByUserOrderByCreatedTimeDesc(User user);

    List<Remittance> findAllByChallengeAndUserOrderByCreatedTimeDesc(Challenge challenge, User user);

}
