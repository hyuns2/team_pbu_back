package projectbuildup.mivv.domain.couponIssuance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.mivv.domain.couponIssuance.entity.CouponIssuance;

public interface CouponIssuanceRepository extends JpaRepository<CouponIssuance, Long> {
}
