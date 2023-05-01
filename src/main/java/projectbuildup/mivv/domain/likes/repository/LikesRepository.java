package projectbuildup.mivv.domain.likes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectbuildup.mivv.domain.likes.entity.Likes;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
}
