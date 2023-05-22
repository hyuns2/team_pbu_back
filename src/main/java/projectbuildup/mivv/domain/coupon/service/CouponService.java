package projectbuildup.mivv.domain.coupon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.coupon.dto.CouponDto;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.coupon.repository.CouponRepository;
import projectbuildup.mivv.domain.couponIssuance.repository.CouponIssuanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.domain.worthyConsumption.entity.CheckConditionType;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.domain.worthyConsumption.repository.WorthyConsumptionRepository;
import projectbuildup.mivv.global.common.imageStore.Image;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
import projectbuildup.mivv.global.error.exception.CBadRequestException;
import projectbuildup.mivv.global.error.exception.CCouponNotFoundException;
import projectbuildup.mivv.global.error.exception.CUserExistException;
import projectbuildup.mivv.global.error.exception.CWorthyConsumptionNotFoundException;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final WorthyConsumptionRepository worthyConsumptionRepository;
    private final UserRepository userRepository;
    private final CouponIssuanceRepository couponIssuanceRepository;

    private final ImageUploader imageUploader;

    /**
     * 가치소비에서 쿠폰을 생성하는 로직입니다.
     * @param
     * @param
     */
    public void createCoupon(Long worthyConsumptionId, CouponDto.Request couponDto) throws IOException {
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException::new);

        Image image = imageUploader.upload(couponDto.getImage(), "coupons");
        Coupon coupon = Coupon.toEntity(couponDto, image.getImagePath());
        worthyConsumption.addCoupon(coupon);
        worthyConsumption.getCondition().checkIssuableCouponStatus(CheckConditionType.OK);
        worthyConsumptionRepository.save(worthyConsumption);
    }
    /**
     * 쿠폰 조회시, 완전한 정보 모두를 포함한 것입니다.
     * @param
     * @return
     */
    public CouponDto.Response readCouponWithWorthyConsumption(Long couponId, Long userId){
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);

        if(couponIssuanceRepository.findByUserAndCoupon(user, coupon).isEmpty())
            throw new CBadRequestException("유저가 보유한 쿠폰이 아닙니다.");

        return new CouponDto.Response(coupon);
    }
    public void updateCoupon(Long couponId, CouponDto.Request couponDto) throws IOException {
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
