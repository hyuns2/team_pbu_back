package projectbuildup.mivv.domain.couponIssuance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.coupon.repository.CouponRepository;
import projectbuildup.mivv.domain.couponIssuance.entity.CouponIssuance;
import projectbuildup.mivv.domain.couponIssuance.repository.CouponIssuanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CBadRequestException;
import projectbuildup.mivv.global.error.exception.CCouponNotFoundException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

@RequiredArgsConstructor
@Service
public class CouponIssuanceService {
    private final CouponIssuanceRepository couponIssuanceRepository;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;

    public void getCoupon(Long userId, Long couponId){
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);
        isIssuable(user, coupon);
        issue(user, coupon);
    }
    public void isIssuable(User user, Coupon coupon){
        if(couponIssuanceRepository.findByUserAndCoupon(user, coupon).isPresent()){
            throw new CBadRequestException("이미 보유중인 쿠폰입니다.");
        }
    }
    public void issue(User user, Coupon coupon){
        CouponIssuance couponIssuance = new CouponIssuance(user, coupon);
        couponIssuanceRepository.save(couponIssuance);
    }
}
