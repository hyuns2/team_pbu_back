package projectbuildup.mivv.domain.coupon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.coupon.dto.request.CouponRequestDto;
import projectbuildup.mivv.domain.coupon.dto.response.CouponResponseDto;
import projectbuildup.mivv.domain.coupon.service.CouponService;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
@Tag(name = "[Coupon]", description = "쿠폰 관련 API입니다.")
public class CouponController {

    private final CouponService couponService;
    @Operation(summary = "쿠폰을 조회합니다.", description = "사용자가 쿠폰을 조회합니다.(단건)")
    //@PreAuthorize("hasRole('USER')")
    @GetMapping("/{couponId}")
    public ResponseEntity<CouponResponseDto.ReadResponseWithWorthyConsumption> readCouponWithWorthyConsumption(@PathVariable(name = "couponId") Long couponId){
        CouponResponseDto.ReadResponseWithWorthyConsumption couponResponseDto = couponService.readCouponWithWorthyConsumption(couponId);
        return new ResponseEntity<>(couponResponseDto, HttpStatus.OK);
    }
    @Operation(summary = "쿠폰의 내용을 수정합니다.", description = "관리자가 쿠폰의 title과 imageUrl을 수정합니다.")
    //@Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{couponId}/content")
    public ResponseEntity<HttpStatus> updateCouponContent(@PathVariable(name = "couponId") Long couponId, @Valid @RequestBody CouponRequestDto.UpdateContentRequest couponRequestDto){
        couponService.updateCouponContent(couponRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "쿠폰의 날짜를 수정합니다.", description = "관리자가 쿠폰의 사용 기한 날짜를 수정합니다.")
    //@Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{couponId}/date")
    public ResponseEntity<HttpStatus> updateCouponDate(@PathVariable(name = "couponId") Long couponId, @Valid @RequestBody CouponRequestDto.UpdateDateRequest couponRequestDto){
        couponService.updateCouponDate(couponRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "쿠폰을 삭제합니다.", description = "관리자가 쿠폰을 삭제합니다.")
    //@Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{couponId}")
    public ResponseEntity<HttpStatus> deleteCoupon(@PathVariable(name = "couponId") Long couponId){
        couponService.deleteCoupon(couponId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
