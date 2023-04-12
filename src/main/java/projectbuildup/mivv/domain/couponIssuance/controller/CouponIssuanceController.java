package projectbuildup.mivv.domain.couponIssuance.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import projectbuildup.mivv.domain.couponIssuance.service.CouponIssuanceService;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "[CouponIssuance]", description = "사용자의 쿠폰 발급 관련 API입니다.")
public class CouponIssuanceController {
    private final CouponIssuanceService couponIssuanceService;

    public ResponseEntity<HttpStatus> issueCouponToUser(Long userId, Long couponId){
        couponIssuanceService.getCoupon(userId, couponId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
