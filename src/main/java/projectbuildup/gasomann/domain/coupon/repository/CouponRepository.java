package projectbuildup.gasomann.domain.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectbuildup.gasomann.domain.coupon.entity.Coupon;
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
