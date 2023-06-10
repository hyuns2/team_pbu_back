package projectbuildup.mivv.domain.remittance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RemittanceRepository extends JpaRepository<Remittance, Long> {

    @Query("select coalesce(sum(r.amount), 0) from Remittance r where r.participation.user = ?1 and r.amount > 0 and r.deletedAt IS NULL ")
    Integer findChargeSum(User user);

    @Query("select coalesce(count(r), 0) from Remittance r where r.participation.user = ?1 and r.amount > 0 and r.deletedAt IS NULL")
    Integer findCountSum(User user);

    @Query("select coalesce(sum(r.amount), 0) from Remittance r where r.participation.user = ?1 and r.amount > 0 and r.modifiedTime between ?2 and ?3 and r.deletedAt IS NULL")
    Integer findChargeSumBetweenTerm(User user, LocalDateTime settingDate, LocalDateTime nowDate);

    @Query("select coalesce(count(r), 0) from Remittance r where r.participation.user = ?1 and r.amount > 0 and r.modifiedTime between ?2 and ?3 and r.deletedAt IS NULL")
    Integer findCountSumBetweenTerm(User user, LocalDateTime settingDate, LocalDateTime nowDate);

    @Query("select r from Remittance r inner join Participation  p on r.participation = p inner join User  u on p.user = u  where u = :user and r.createdTime between :startTime and :endTime and r.deletedAt IS NULL")
    List<Remittance> findByUserAndCreatedTimeBetween(@Param("user") User user, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);


    @Query("select r from Remittance r inner join Participation  p on r.participation = p inner join User  u on p.user = u  where u = :user and r.amount > 0 and r.createdTime between :startTime and :endTime and r.deletedAt IS NULL")
    List<Remittance> findByUserAndDepositAndCreatedTimeBetween(@Param("user") User user, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("select coalesce(sum(r.amount), 0) from Remittance r inner join Participation  p on r.participation = p inner join User  u on p.user = u  where u = :user and r.deletedAt IS NULL")
    Long findSumAmountByUser(@Param("user") User user);

    @Query("select coalesce(sum(r.amount), 0) from Remittance r inner join Participation  p on r.participation = p inner join User  u on p.user = u  where u = :user and r.createdTime between :startTime and :endTime and r.deletedAt IS NULL")
    Long findSumAmountByUserAndCreatedTimeBetween(@Param("user") User user, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("select coalesce(sum(r.amount), 0) from Remittance r inner join Participation  p on r.participation = p inner join User  u on p.user = u  inner join Challenge  c on p.challenge = c where u = :user and c = :challenge and r.deletedAt IS NULL")
    Long findSumAmountByUserAndChallenge(@Param("user") User user, @Param("challenge") Challenge challenge);
}
