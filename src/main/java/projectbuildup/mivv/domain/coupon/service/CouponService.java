package projectbuildup.mivv.domain.coupon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.coupon.dto.request.CouponRequestDto;
import projectbuildup.mivv.domain.coupon.dto.response.CouponResponseDto;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.coupon.repository.CouponRepository;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.domain.worthyConsumption.repository.WorthyConsumptionRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final WorthyConsumptionRepository worthyConsumptionRepository;

    public void createCoupon(Long worthyConsumptionId, CouponRequestDto.CreationRequest couponRequestDto){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow();
        Coupon coupon = couponRequestDto.toEntity();
        worthyConsumption.addCoupon(coupon);
        worthyConsumptionRepository.save(worthyConsumption);

    }
    public CouponResponseDto.ReadResponseWithWorthyConsumption readCouponWithWorthyConsumption(Long couponId){
        Coupon coupon = couponRepository.findById(couponId).orElseThrow();
        return new CouponResponseDto.ReadResponseWithWorthyConsumption(coupon);
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
