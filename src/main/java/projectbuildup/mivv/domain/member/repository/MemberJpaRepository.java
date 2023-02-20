package projectbuildup.mivv.domain.member.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.mivv.domain.member.entity.Member;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long>{
    Optional<Member> findByAccount(String account);
}
