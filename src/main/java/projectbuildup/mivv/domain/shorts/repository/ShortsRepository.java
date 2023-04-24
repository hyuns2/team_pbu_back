package projectbuildup.mivv.domain.shorts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectbuildup.mivv.domain.shorts.entity.Shorts;
import projectbuildup.mivv.domain.shorts.entity.ShortsCategory;

import java.util.List;
@Repository
public interface ShortsRepository extends JpaRepository<Shorts, Long> {
    //List<Shorts> findAllByShortsCategory(ShortsCategory category);
}
