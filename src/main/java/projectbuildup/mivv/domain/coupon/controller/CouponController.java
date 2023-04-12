package projectbuildup.mivv.domain.coupon.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.coupon.dto.request.CouponRequestDto;
import projectbuildup.mivv.domain.coupon.dto.response.CouponResponseDto;
import projectbuildup.mivv.domain.coupon.service.CouponService;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
@Tag(name = "[Coupon]", description = "쿠폰 관련 API입니다.")
public class CouponController {
    private final CouponService couponService;
    @GetMapping("/{couponId}")
    public ResponseEntity<CouponResponseDto.ReadResponseWithWorthyConsumption> readCouponWithWorthyConsumption(@PathVariable(name = "couponId") Long couponId){
        CouponResponseDto.ReadResponseWithWorthyConsumption couponResponseDto = couponService.readCouponWithWorthyConsumption(couponId);
        return new ResponseEntity<>(couponResponseDto, HttpStatus.OK);
    }
    @PutMapping("/{couponId}/content")
    public ResponseEntity<HttpStatus> updateCouponContent(@PathVariable(name = "couponId") Long couponId, @Valid @RequestBody CouponRequestDto.UpdateContentRequest couponRequestDto){
        couponService.updateCouponContent(couponRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{couponId}/date")
    public ResponseEntity<HttpStatus> updateCouponDate(@PathVariable(name = "couponId") Long couponId, @Valid @RequestBody CouponRequestDto.UpdateDateRequest couponRequestDto){
        couponService.updateCouponDate(couponRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{couponId}")
    public ResponseEntity<HttpStatus> deleteCoupon(@PathVariable(name = "couponId") Long couponId){
        couponService.deleteCoupon(couponId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
