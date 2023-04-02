package projectbuildup.mivv.domain.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.coupon.dto.request.CouponRequestDto;
import projectbuildup.mivv.domain.coupon.dto.response.CouponResponseDto;
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
    public CouponResponseDto.ReadResponse readCoupon(CouponRequestDto.ReadRequest couponRequestDto){
        Coupon coupon = couponRepository.findById(couponRequestDto.getId()).orElseThrow();
        return new CouponResponseDto.ReadResponse(coupon);
    }
    public void updateCouponContent(CouponRequestDto.UpdateContentRequest couponRequestDto){
        Coupon coupon = couponRepository.findById(couponRequestDto.getId()).orElseThrow();
        coupon.updateContent(couponRequestDto);
        couponRepository.save(coupon);
    }
    public void updateCouponPrice(CouponRequestDto.UpdatePriceRequest couponRequestDto){
        Coupon coupon = couponRepository.findById(couponRequestDto.getId()).orElseThrow();
        coupon.updatePrice(couponRequestDto);
        couponRepository.save(coupon);
    }
}
