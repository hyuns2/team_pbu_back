package projectbuildup.mivv.domain.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.mivv.domain.challenge.entity.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
