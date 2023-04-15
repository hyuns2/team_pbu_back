package projectbuildup.mivv.domain.archiving.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectbuildup.mivv.domain.archiving.entity.CardEntity;

@Repository
public interface CardRepository<T extends CardEntity> extends JpaRepository<T, Long> {

}
