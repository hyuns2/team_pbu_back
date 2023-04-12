package projectbuildup.mivv.domain.couponIssuance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.couponIssuance.entity.CouponIssuance;
import projectbuildup.mivv.domain.user.entity.User;

import java.util.Optional;

public interface CouponIssuanceRepository extends JpaRepository<CouponIssuance, Long> {
    Optional<CouponIssuance> findByUserAndCoupon(User user, Coupon coupon);
    Optional<CouponIssuance> findAllByUserId(Long userId);
    CouponIssuance findByUserIdAndCouponId(Long userId, Long couponId);

}
