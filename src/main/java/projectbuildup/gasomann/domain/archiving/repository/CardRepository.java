package projectbuildup.gasomann.domain.archiving.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectbuildup.gasomann.domain.archiving.entity.Card;

@Repository
public interface CardRepository<T extends Card> extends JpaRepository<T, Long>, CustomCardRepository {

}
