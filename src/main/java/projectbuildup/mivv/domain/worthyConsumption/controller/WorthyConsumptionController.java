package projectbuildup.mivv.domain.worthyConsumption.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.coupon.dto.request.CouponRequestDto;
import projectbuildup.mivv.domain.coupon.service.CouponService;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionRequestDto;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionUrlRequestDto;
import projectbuildup.mivv.domain.worthyConsumption.dto.response.WorthyConsumptionResponseDto;
import projectbuildup.mivv.domain.worthyConsumption.service.WorthyConsumptionService;
import projectbuildup.mivv.domain.worthyConsumption.service.WorthyConsumptionValidationService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/worthyConsumptions")
@Tag(name = "[WorthyConsumption]", description = "가치소비 관련 API입니다.")
public class WorthyConsumptionController {
    private final WorthyConsumptionService worthyConsumptionService;
    private final WorthyConsumptionValidationService worthyConsumptionValidationService;
    private final CouponService couponService;
    @PostMapping
    public ResponseEntity<HttpStatus> createWorthyConsumption(@Valid @RequestBody WorthyConsumptionRequestDto.CreationRequest worthyConsumptionRequestDto){
            worthyConsumptionService.createWorthyConsumption(worthyConsumptionRequestDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/{worthyConsumptionId}/summary")
    public ResponseEntity<WorthyConsumptionResponseDto.ReadSummaryResponse> getWorthyConsumptionSummary(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId){
        WorthyConsumptionResponseDto.ReadSummaryResponse worthyConsumptionResponseDto = worthyConsumptionService.readSummaryWorthyConsumption(worthyConsumptionId);
        return new ResponseEntity<>(worthyConsumptionResponseDto, HttpStatus.OK);
    }
    @GetMapping("/{worthyConsumptionId}/basic")
    public ResponseEntity<WorthyConsumptionResponseDto.ReadBasicResponse> getWorthyConsumptionBasic(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId){
        WorthyConsumptionResponseDto.ReadBasicResponse WorthyConsumptionResponseDto = worthyConsumptionService.readBasicWorthyConsumption(worthyConsumptionId);
        return new ResponseEntity<>(WorthyConsumptionResponseDto, HttpStatus.OK);
    }
    @GetMapping("/{worthyConsumptionId}/detail")
    public ResponseEntity<WorthyConsumptionResponseDto.ReadDetailResponse> getWorthyConsumptionDetail(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId){
        WorthyConsumptionResponseDto.ReadDetailResponse WorthyConsumptionResponseDto = worthyConsumptionService.readDetailWorthyConsumption(worthyConsumptionId);
        return new ResponseEntity<>(WorthyConsumptionResponseDto, HttpStatus.OK);
    }
    @PutMapping("/{worthyConsumptionId}/content")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionContent(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @Valid @RequestBody WorthyConsumptionRequestDto.UpdateContentRequest worthyConsumptionRequestDto){
        worthyConsumptionValidationService.isSameWorthyConsumptionId(worthyConsumptionId, worthyConsumptionRequestDto.getId());
        worthyConsumptionService.updateContentWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{worthyConsumptionId}/url")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionUrl(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @Valid @RequestBody WorthyConsumptionRequestDto.UpdateUrlRequest worthyConsumptionRequestDto){
        worthyConsumptionValidationService.isSameWorthyConsumptionId(worthyConsumptionId, worthyConsumptionRequestDto.getId());
        worthyConsumptionService.updateUrlWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{worthyConsumptionId}/price")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionPrice(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @Valid @RequestBody WorthyConsumptionRequestDto.UpdatePriceRequest worthyConsumptionRequestDto){
        worthyConsumptionValidationService.isSameWorthyConsumptionId(worthyConsumptionId, worthyConsumptionRequestDto.getId());
        worthyConsumptionService.updatePriceWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{worthyConsumptionId}/place")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionPlace(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @Valid @RequestBody WorthyConsumptionRequestDto.UpdatePlaceRequest worthyConsumptionRequestDto){
        worthyConsumptionValidationService.isSameWorthyConsumptionId(worthyConsumptionId, worthyConsumptionRequestDto.getId());
        worthyConsumptionService.updatePlaceWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{worthyConsumptionId}/date")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionDate(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @Valid @RequestBody WorthyConsumptionRequestDto.UpdateIssuableCouponDateRequest worthyConsumptionRequestDto){
        worthyConsumptionValidationService.isSameWorthyConsumptionId(worthyConsumptionId, worthyConsumptionRequestDto.getId());
        worthyConsumptionService.updateIssuableCouponDate(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{worthyConsumptionId}")
    public ResponseEntity<HttpStatus> deleteWorthyConsumption(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId){
        worthyConsumptionService.deleteWorthyConsumption(worthyConsumptionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/{worthyConsumptionId}")
    public ResponseEntity<HttpStatus> createCoupon(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @Valid @RequestBody CouponRequestDto.CreationRequest couponRequestDto){
        couponService.createCoupon(worthyConsumptionId, couponRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
