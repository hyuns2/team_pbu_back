package projectbuildup.gasomann.domain.challenge.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectbuildup.gasomann.domain.challenge.entity.Challenge;
import projectbuildup.gasomann.domain.user.entity.User;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    @Query("select c from  Challenge c inner join Participation p on p.challenge = c where p.user = :user and p.deletedAt IS NULL")
    Page<Challenge> findOngoingChallenge(@Param("user") User user, Pageable pageable);

    @Query("select ac from Challenge ac where not exists (select c from  Challenge c inner join Participation p on p.challenge = c where p.user = :user and ac.id = c.id and p.deletedAt IS NULL) and ac.closed = false  ")
    Page<Challenge> findJoinableChallenge(@Param("user") User user, Pageable pageable);

    Page<Challenge> findAllByClosedFalse(Pageable pageable);

}
