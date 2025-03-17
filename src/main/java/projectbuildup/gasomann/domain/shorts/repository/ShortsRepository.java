package projectbuildup.gasomann.domain.shorts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectbuildup.gasomann.domain.shorts.entity.Shorts;

@Repository
public interface ShortsRepository extends JpaRepository<Shorts, Long> {
}
