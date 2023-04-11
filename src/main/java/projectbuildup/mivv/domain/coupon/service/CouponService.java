package projectbuildup.mivv.domain.coupon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.coupon.dto.request.CouponRequestDto;
import projectbuildup.mivv.domain.coupon.dto.response.CouponResponseDto;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.coupon.repository.CouponRepository;
@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponRepository couponRepository;

    public void createCoupon(CouponRequestDto.CreationRequest couponRequestDto){
        Coupon coupon = couponRequestDto.toEntity();
        couponRepository.save(coupon);
    }
    public CouponResponseDto.ReadResponse readCoupon(Long couponId){
        Coupon coupon = couponRepository.findById(couponId).orElseThrow();
        return new CouponResponseDto.ReadResponse(coupon);
    }
    public void updateCouponContent(CouponRequestDto.UpdateContentRequest couponRequestDto){
        Coupon coupon = couponRepository.findById(couponRequestDto.getId()).orElseThrow();
        coupon.updateContent(couponRequestDto);
        couponRepository.save(coupon);
    }
    public void updateCouponDate(CouponRequestDto.UpdateDateRequest couponRequestDto){
        Coupon coupon = couponRepository.findById(couponRequestDto.getId()).orElseThrow();
        coupon.updateDate(couponRequestDto);
        couponRepository.save(coupon);
    }
    public void deleteCoupon(Long couponId){
        //couponRepository.deleteById(couponRequestDto.getId());
        Coupon coupon = couponRepository.findById(couponId).orElseThrow();
        couponRepository.delete(coupon);
    }
}
