package projectbuildup.mivv.domain.worthyConsumption.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.coupon.dto.request.CouponRequestDto;
import projectbuildup.mivv.domain.coupon.service.CouponService;
import projectbuildup.mivv.domain.worthyConsumption.dto.WorthyConsumptionConditionDto;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionRequestDto;
import projectbuildup.mivv.domain.worthyConsumption.dto.response.WorthyConsumptionResponseDto;
import projectbuildup.mivv.domain.worthyConsumption.service.WorthyConsumptionService;
import projectbuildup.mivv.domain.worthyConsumption.service.WorthyConsumptionValidationService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/worthyConsumptions")
@Tag(name = "[WorthyConsumption]", description = "가치소비 관련 API입니다.")
public class WorthyConsumptionController {
    private final WorthyConsumptionService worthyConsumptionService;
    private final WorthyConsumptionValidationService worthyConsumptionValidationService;
    private final CouponService couponService;

    /**
     * 가치소비를 생성합니다.
     * @param worthyConsumptionRequestDto
     * @return
     */
    @PostMapping
    public ResponseEntity<HttpStatus> createWorthyConsumption(@Valid @RequestBody WorthyConsumptionRequestDto.CreationRequest worthyConsumptionRequestDto){
            worthyConsumptionService.createWorthyConsumption(worthyConsumptionRequestDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 가치소비를 조회합니다.
     * summary는 내가 찜한 가치소비 페이지에 들어갈 정보입니다.
     * basic은 소비추천화면입니다. (SCR_Value)에 해당하는 정보입니다.
     * detail은 소비추천화면에서 더 알아보기를 눌렀을 경우 가치소비에 대한 상세 정보를 담았습니다.
     *
     * basic을 조회할 때, 발급가능한 쿠폰이 있는지 판단하는 로직이 필요하고, 이 정보를 담은 필드도 필요하다고 판단해 해당 서비스 로직과 DTO를 구성하였습니다.
     *
     * @param worthyConsumptionId
     * @return
     */
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
    @GetMapping
    public ResponseEntity<List<WorthyConsumptionResponseDto.ReadBasicResponse>> getAllWorthyConsumption(){
        List<WorthyConsumptionResponseDto.ReadBasicResponse> worthyConsumptionResponseDtos = worthyConsumptionService.readAllWorthyConsumption();
        return new ResponseEntity<>(worthyConsumptionResponseDtos, HttpStatus.OK);
    }

    /**
     *
     * 가치소비의 여러 정보 수정 로직들 입니다.
     * content는 제목, 내용, 태그 등
     * url은 가치소비의 이미지나 영상의 url
     * price는 가치소비의 가격, 가격태그
     * place는 가치소비의 위치
     * date는 가치소비의 날짜에 해당합니다.
     *
     * @param worthyConsumptionId
     * @param worthyConsumptionRequestDto
     * @return
     */
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
    public ResponseEntity<HttpStatus> updateWorthyConsumptionDate(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @Valid @RequestBody WorthyConsumptionConditionDto.UpdateIssuableCouponDateRequest worthyConsumptionRequestDto){
        worthyConsumptionValidationService.isSameWorthyConsumptionId(worthyConsumptionId, worthyConsumptionRequestDto.getId());
        worthyConsumptionService.updateIssuableCouponDate(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{worthyConsumptionId}/condition")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionCondition(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @Valid @RequestBody WorthyConsumptionConditionDto.UpdateConditionRequest worthyConsumptionRequestDto){
        worthyConsumptionValidationService.isSameWorthyConsumptionId(worthyConsumptionId, worthyConsumptionRequestDto.getId());
        worthyConsumptionService.updateCouponCondition(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 가치소비를 삭제합니다.
     *
     * @param worthyConsumptionId
     * @return
     */
    @DeleteMapping("/{worthyConsumptionId}")
    public ResponseEntity<HttpStatus> deleteWorthyConsumption(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId){
        worthyConsumptionService.deleteWorthyConsumption(worthyConsumptionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 가치소비에서 쿠폰을 생성하는 로직입니다.
     *
     * @param worthyConsumptionId
     * @param couponRequestDto
     * @return
     */
    @PostMapping("/{worthyConsumptionId}")
    public ResponseEntity<HttpStatus> createCoupon(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @Valid @RequestBody CouponRequestDto.CreationRequest couponRequestDto){
        couponService.createCoupon(worthyConsumptionId, couponRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
