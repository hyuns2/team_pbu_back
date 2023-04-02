package projectbuildup.mivv.domain.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import projectbuildup.mivv.domain.coupon.dto.request.CouponRequestDto;
import projectbuildup.mivv.domain.coupon.service.CouponService;
@RequiredArgsConstructor
@RestController
public class CouponController {
    private final CouponService couponService;

    public ResponseEntity<HttpStatus> createCoupon(CouponRequestDto.CreationRequest couponRequestDto){
        couponService.createCoupon(couponRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
