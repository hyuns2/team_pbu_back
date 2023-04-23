package projectbuildup.mivv.domain.shorts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.mivv.domain.shorts.entity.Shorts;

public interface ShortsRepository extends JpaRepository<Shorts, Long> {

}
