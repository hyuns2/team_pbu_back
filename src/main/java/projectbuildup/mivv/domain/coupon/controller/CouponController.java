package projectbuildup.mivv.domain.coupon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.coupon.dto.CouponDto;
import projectbuildup.mivv.domain.coupon.service.CouponService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

import java.io.IOException;
import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
@Tag(name = "[Coupon]", description = "쿠폰 관련 API입니다.")
public class CouponController {

    private final CouponService couponService;
    @Operation(summary = "쿠폰을 모두 조회합니다.", description = "관리자가 쿠폰을 모두 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<?> readAllCoupon(){
        List<CouponDto.Response> couponList = couponService.readAllCoupon();
        return new ResponseEntity<>(couponList, HttpStatus.OK);
    }
    @Operation(summary = "쿠폰을 조회합니다.", description = "사용자가 쿠폰을 조회합니다.(단건)")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{couponId}")
    public ResponseEntity<CouponDto.Response> readCouponWithWorthyConsumption(@PathVariable(name = "couponId") Long couponId, @AuthenticationPrincipal User user){
        CouponDto.Response couponResponseDto = couponService.readCouponWithWorthyConsumption(couponId, user.getId());
        return new ResponseEntity<>(couponResponseDto, HttpStatus.OK);
    }
    @Operation(summary = "쿠폰을 수정합니다.", description = "관리자가 쿠폰을 수정합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{couponId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> updateCoupon(@PathVariable(name = "couponId")Long couponId, @Valid @ModelAttribute("updateCoupon") CouponDto.Request couponDto) throws IOException {
        couponService.updateCoupon(couponId, couponDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "쿠폰을 삭제합니다.", description = "관리자가 쿠폰을 삭제합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{couponId}")
    public ResponseEntity<HttpStatus> deleteCoupon(@PathVariable(name = "couponId") Long couponId){
        couponService.deleteCoupon(couponId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "쿠폰을 발급받은 유저정보를 쿠폰 종류별로 조회합니다.", description = "관리자가 쿠폰 Id를 입력하면, 해당 쿠폰을 발급받은 유저 정보를 엑셀파일로 반환합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/excel/{couponId}")
    public void retrieveExcelByCouponType(@AuthenticationPrincipal User user, HttpServletResponse response, @PathVariable("couponId") Long couponId) throws IOException {
        couponService.retrieveExcelByCouponType(response, couponId);
    }

    @Operation(summary = "쿠폰을 발급받은 유저정보를 월별로 조회합니다.", description = "관리자가 쿠폰 Id와 연도, 월을 입력하면, 해당 쿠폰을 발급받은 유저 정보를 엑셀파일로 반환합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/excel/{year}/{month}")
    public void retrieveExcelByCouponDate(@AuthenticationPrincipal User user, HttpServletResponse response, @PathVariable("year") int year, @PathVariable("month") int month) throws IOException {
        couponService.retrieveExcelByCouponDate(response, year, month);
    }

}
