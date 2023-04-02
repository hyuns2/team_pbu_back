package projectbuildup.mivv.domain.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import projectbuildup.mivv.domain.coupon.dto.request.CouponRequestDto;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.coupon.repository.CouponRepository;
@Service
@RequiredArgsConstructor
public class CouponValidationService {
    /**
     * 발행 X 사용 O -> ERROR
     */
    private final CouponRepository couponRepository;

//    public void createValidationCoupon(CouponRequestDto coupon){
//        if(coupon.getUse()==false)
//        //exeptionError 던지기
//    }
}
