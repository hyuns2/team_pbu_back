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
import projectbuildup.mivv.global.error.exception.CCouponNotFoundException;
import projectbuildup.mivv.global.error.exception.CWorthyConsumptionNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final WorthyConsumptionRepository worthyConsumptionRepository;

    /**
     * 가치소비에서 쿠폰을 생성하는 로직입니다.
     * @param worthyConsumptionId
     * @param couponRequestDto
     */
    public void createCoupon(Long worthyConsumptionId, CouponRequestDto.CreationRequest couponRequestDto){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException::new);
        Coupon coupon = new Coupon(couponRequestDto);
        //Coupon coupon = couponRequestDto.toEntity();//주입할거가 없다면 빌더 패턴 말고 그냥 new 해야하는건가?
        worthyConsumption.addCoupon(coupon);
        worthyConsumptionRepository.save(worthyConsumption);

    }

    /**
     * 쿠폰 조회시, 완전한 정보 모두를 포함한 것입니다.
     * @param couponId
     * @return
     */
    public CouponResponseDto.ReadResponseWithWorthyConsumption readCouponWithWorthyConsumption(Long couponId){
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);
        return new CouponResponseDto.ReadResponseWithWorthyConsumption(coupon);
    }
    public void updateCouponContent(CouponRequestDto.UpdateContentRequest couponRequestDto){
        Coupon coupon = couponRepository.findById(couponRequestDto.getId()).orElseThrow(CCouponNotFoundException::new);
        coupon.updateContent(couponRequestDto);
        couponRepository.save(coupon);
    }
    public void updateCouponDate(CouponRequestDto.UpdateDateRequest couponRequestDto){
        Coupon coupon = couponRepository.findById(couponRequestDto.getId()).orElseThrow(CCouponNotFoundException::new);
        coupon.updateDate(couponRequestDto);
        couponRepository.save(coupon);
    }
    public void deleteCoupon(Long couponId){
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);
        couponRepository.delete(coupon);
    }
}
