package projectbuildup.mivv.domain.shorts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.mivv.domain.shorts.entity.Shorts;
import projectbuildup.mivv.domain.shorts.entity.ShortsType;

import java.util.List;

public interface ShortsRepository extends JpaRepository<Shorts, Long> {

    List<Shorts> findAllByShortType(ShortsType shortsType);
}
