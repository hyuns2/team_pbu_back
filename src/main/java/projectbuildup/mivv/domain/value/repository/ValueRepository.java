package projectbuildup.mivv.domain.value.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectbuildup.mivv.domain.value.entity.Value;
@Repository
public interface ValueRepository extends JpaRepository<Value, Long> {
}
