package projectbuildup.mivv.domain.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.coupon.dto.request.CouponRequestDto;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.coupon.repository.CouponRepository;
@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponRepository couponRepository;

    public void createCoupon(CouponRequestDto.CreationRequest couponRequestDto){
        Coupon coupon = couponRequestDto.toEntity();
        couponRepository.save(coupon);
    }
}
