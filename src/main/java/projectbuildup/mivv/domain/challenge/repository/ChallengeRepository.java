package projectbuildup.mivv.domain.challenge.repository;

import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {



}
