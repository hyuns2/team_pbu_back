package projectbuildup.mivv.domain.coupon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.coupon.dto.CouponDto;
import projectbuildup.mivv.domain.coupon.dto.response.CouponResponseDto;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.coupon.repository.CouponRepository;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.domain.worthyConsumption.repository.WorthyConsumptionRepository;
import projectbuildup.mivv.global.common.imageStore.Image;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
import projectbuildup.mivv.global.error.exception.CCouponNotFoundException;
import projectbuildup.mivv.global.error.exception.CWorthyConsumptionNotFoundException;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final WorthyConsumptionRepository worthyConsumptionRepository;

    private final ImageUploader imageUploader;

    /**
     * 가치소비에서 쿠폰을 생성하는 로직입니다.
     * @param worthyConsumptionId
     * @param
     */
    public void createCoupon(Long worthyConsumptionId, CouponDto.Creation couponDto) throws IOException {
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException::new);

        Image image = imageUploader.upload(couponDto.getImage(), "coupons");
        Coupon coupon = new Coupon(couponDto, image.getImagePath());
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
    public void updateCoupon(Long couponId, CouponDto.Update couponDto) throws IOException {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);
        Image image = imageUploader.upload(couponDto.getImage(), "coupons");
        String imagePath = image.getImagePath();
        coupon.update(couponDto, imagePath);
        couponRepository.save(coupon);
    }
    public void deleteCoupon(Long couponId){
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);
        couponRepository.delete(coupon);
    }
}
