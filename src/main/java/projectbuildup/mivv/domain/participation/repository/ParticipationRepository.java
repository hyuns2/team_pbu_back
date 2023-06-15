package projectbuildup.mivv.domain.participation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    Optional<Participation> findByChallengeAndUser(Challenge challenge, User user);
    Optional<Participation> findByChallengeAndUserAndClosedFalse(Challenge challenge, User user);

    void deleteByChallengeAndUser(Challenge challenge, User user);

    List<Participation> findAllByChallenge(Challenge challenge);
    List<Participation> findAllByUser(User user);

    Page<Participation> findAllByUser(User user, Pageable pageable);


    void deleteAllByUser(User user);
}
