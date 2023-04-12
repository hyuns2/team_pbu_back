package projectbuildup.mivv.domain.couponIssuance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectbuildup.mivv.domain.couponIssuance.service.CouponIssuanceService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "[Coupon Issue]", description = "사용자의 쿠폰 발급 관련 API입니다.")
public class CouponIssuanceController {
    private final CouponIssuanceService couponIssuanceService;
    @Operation(summary = "사용자가 쿠폰을 발급 받습니다.", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/issue/coupons/{couponId}")
    public ResponseEntity<HttpStatus> issueCouponToUser(@AuthenticationPrincipal User user, @PathVariable("couponId") Long couponId){
        couponIssuanceService.getCoupon(user.getId(), couponId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
