package projectbuildup.mivv.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.mivv.domain.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
