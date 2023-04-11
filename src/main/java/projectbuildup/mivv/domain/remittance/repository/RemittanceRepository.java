package projectbuildup.mivv.domain.remittance.repository;


import io.netty.util.internal.IntegerHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RemittanceRepository extends JpaRepository<Remittance, Long> {

    List<Remittance> findAllByCreatedTimeAfter(LocalDateTime time);
    List<Remittance> findAllByUserAndCreatedTimeBetween(User user, LocalDateTime startDate, LocalDateTime endDate);

    @Query("select sum(r.amount) from Remittance r where r.user = ?1 and r.amount > 0")
    Integer findChargeSum(User user);

    @Query("select count(r) from Remittance r where r.user = ?1 and r.amount > 0")
    Integer findCountSum(User user);

    @Query("select sum(r.amount) from Remittance r where r.user = ?1 and r.amount > 0 and r.modifiedTime between ?2 and ?3")
    Integer findChargeSumBetweenTerm(User user, LocalDateTime settingDate, LocalDateTime nowDate);

    @Query("select count(r) from Remittance r where r.user = ?1 and r.amount > 0 and r.modifiedTime between ?2 and ?3")
    Integer findCountSumBetweenTerm(User user, LocalDateTime settingDate, LocalDateTime nowDate);

}
