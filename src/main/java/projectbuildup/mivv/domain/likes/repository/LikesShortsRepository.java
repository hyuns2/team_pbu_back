package projectbuildup.mivv.domain.likes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectbuildup.mivv.domain.likes.entity.LikesCategory;
import projectbuildup.mivv.domain.likes.entity.LikesShorts;
import projectbuildup.mivv.domain.shorts.entity.Shorts;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesShortsRepository extends JpaRepository<LikesShorts, Long> {
    void deleteLikesShortsByUserAndShorts(User user, Shorts shorts);
    List<LikesShorts> findAllByUser(User user);
    Optional<LikesShorts> findByUserAndShorts(User user, Shorts shorts);
    List<LikesShorts> findAllByUserAndLikesCategory(User user, LikesCategory likesCategory);
}
