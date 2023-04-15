package projectbuildup.mivv.domain.remittance.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface RemittanceRepository extends JpaRepository<Remittance, Long> {

    @Query("select r from Remittance r inner join Participation  p on r.participation = p inner join User  u on p.user = u  where u = :user and r.createdTime between :startTime and :endTime ")
    List<Remittance> findByUserAndCreatedTimeBetween(@Param("user") User user, @Param("startTime")LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("select r from Remittance r inner join Participation  p on r.participation = p inner join User  u on p.user = u  where u = :user and r.amount > 0 and r.createdTime between :startTime and :endTime ")
    List<Remittance> findByUserAndDepositAndCreatedTimeBetween(@Param("user") User user, @Param("startTime")LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("select sum(r.amount) from Remittance r inner join Participation  p on r.participation = p inner join User  u on p.user = u  where u = :user ")
    long findSumAmountByUser(@Param("user") User user);

    List<Remittance> findAllByCreatedTimeAfter(LocalDateTime now);
    
}
