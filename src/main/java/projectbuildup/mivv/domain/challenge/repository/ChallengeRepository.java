package projectbuildup.mivv.domain.challenge.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    @Query("select c from  Challenge c inner join Participation p on p.challenge = c where p.user = :user ")
    Page<Challenge> findOngoingChallenge(@Param("user") User user, Pageable pageable);

    @Query("select ac from Challenge ac where not exists (select c from  Challenge c inner join Participation p on p.challenge = c where p.user = :user and ac.id = c.id ) ")
    Page<Challenge> findJoinableChallenge(@Param("user") User user, Pageable pageable);

}
