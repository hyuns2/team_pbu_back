package projectbuildup.mivv.domain.remittance.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RemittanceRepository extends JpaRepository<Remittance, Long> {

    List<Remittance> findAllByCreatedTimeAfter(LocalDateTime time);
    List<Remittance> findAllByUserAndCreatedTimeBetween(User user, LocalDateTime startDate, LocalDateTime endDate);
}
