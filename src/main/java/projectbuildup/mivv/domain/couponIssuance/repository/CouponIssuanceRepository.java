package projectbuildup.mivv.domain.couponIssuance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.couponIssuance.entity.CouponIssuance;
import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CouponIssuanceRepository extends JpaRepository<CouponIssuance, Long> {
    Optional<CouponIssuance> findByUserAndCoupon(User user, Coupon coupon);
    List<CouponIssuance> findAllByUserId(Long userId);
    CouponIssuance findByUserIdAndCouponId(Long userId, Long couponId);
    int countByCouponId(Long couponId);

    List<CouponIssuance> findAllByCoupon(Coupon coupon);

    @Query("select c.createdTime from CouponIssuance c where c.user = ?1 order by c.createdTime DESC")
    List<LocalDateTime> findCreatedTimeByUserId(User user);

    void deleteAllByUser(User user);
}
