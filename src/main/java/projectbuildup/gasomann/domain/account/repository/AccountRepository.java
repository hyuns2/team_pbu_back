package projectbuildup.gasomann.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.gasomann.domain.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
