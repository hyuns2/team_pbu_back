package projectbuildup.gasomann.domain.remittance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectbuildup.gasomann.domain.challenge.entity.Challenge;
import projectbuildup.gasomann.domain.remittance.entity.Remittance;
import projectbuildup.gasomann.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface RemittanceRepository extends JpaRepository<Remittance, Long> {
    @Query("select coalesce(sum(r.amount), 0) as chargeSum, coalesce(count(r), 0) as countSum from Remittance r where r.participation.user = :user and r.amount > 0")
    ReportForCard findChargeSumAndCountSumByUser(User user);

    @Query("select coalesce(sum(r.amount), 0) as chargeSum, coalesce(count(r), 0) as countSum from Remittance r where r.participation.user = :user and r.amount > 0 and r.modifiedTime between :startDate and :endDate")
    ReportForCard findChargeSumAndCountSumByUserAndTermBetween(User user, LocalDateTime startDate, LocalDateTime endDate);

    @Query("select r from Remittance r inner join Participation  p on r.participation = p inner join User  u on p.user = u  where u = :user and r.createdTime between :startTime and :endTime and r.deletedAt IS NULL order by r.createdTime DESC ")
    List<Remittance> findByUserAndYearMonth(@Param("user") User user, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("select r from Remittance r inner join Participation  p on r.participation = p inner join User  u on p.user = u  where u = :user and r.amount > 0 and r.createdTime between :startTime and :endTime and r.deletedAt IS NULL")
    List<Remittance> findByUserAndDepositAndCreatedTimeBetween(@Param("user") User user, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("select coalesce(sum(r.amount), 0) from Remittance r inner join Participation  p on r.participation = p inner join User  u on p.user = u  where u = :user and r.deletedAt IS NULL")
    Long findSumAmountByUser(@Param("user") User user);

    @Query("select coalesce(sum(r.amount), 0) from Remittance r inner join Participation  p on r.participation = p inner join User  u on p.user = u  where u = :user and r.createdTime between :startTime and :endTime and r.deletedAt IS NULL")
    Long findSumAmountByUserAndCreatedTimeBetween(@Param("user") User user, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("select coalesce(sum(r.amount), 0) from Remittance r inner join Participation  p on r.participation = p inner join User  u on p.user = u  inner join Challenge  c on p.challenge = c where u = :user and c = :challenge and r.deletedAt IS NULL")
    Long findSumAmountByUserAndChallenge(@Param("user") User user, @Param("challenge") Challenge challenge);
}
