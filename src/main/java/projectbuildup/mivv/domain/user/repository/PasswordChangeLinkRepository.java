package projectbuildup.mivv.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import projectbuildup.mivv.domain.user.entity.PasswordChangeLink;

import java.util.Optional;

public interface PasswordChangeLinkRepository extends CrudRepository<PasswordChangeLink, Long> {

    Optional<PasswordChangeLink> findBySerialNumber(String serialNumber);
}
