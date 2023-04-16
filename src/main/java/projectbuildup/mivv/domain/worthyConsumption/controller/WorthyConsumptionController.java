package projectbuildup.mivv.domain.worthyConsumption.controller;

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
import projectbuildup.mivv.domain.coupon.service.CouponService;
import projectbuildup.mivv.domain.worthyConsumption.dto.WorthyConsumptionConditionDto;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionRequestDto;
import projectbuildup.mivv.domain.worthyConsumption.dto.response.WorthyConsumptionResponseDto;
import projectbuildup.mivv.domain.worthyConsumption.service.WorthyConsumptionService;
import projectbuildup.mivv.domain.worthyConsumption.service.WorthyConsumptionValidationService;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

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
    @Operation(summary = "가치소비 생성", description = "가치소비를 등록합니다.")
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
    @Operation(summary = "가치소비의 요약본 조회", description = "찜, 가치소비 안 작은 네모에 들어가는 정보를 요약본이라고 하며, 이를 조회합니다.")
    @GetMapping("/{worthyConsumptionId}/summary")
    public ResponseEntity<WorthyConsumptionResponseDto.ReadSummaryResponse> getWorthyConsumptionSummary(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId){
        WorthyConsumptionResponseDto.ReadSummaryResponse worthyConsumptionResponseDto = worthyConsumptionService.readSummaryWorthyConsumption(worthyConsumptionId);
        return new ResponseEntity<>(worthyConsumptionResponseDto, HttpStatus.OK);
    }
    @Operation(summary = "가치소비 조회", description = "가치소비를 조회합니다.")
    @GetMapping("/{worthyConsumptionId}/basic")
    public ResponseEntity<WorthyConsumptionResponseDto.ReadBasicResponse> getWorthyConsumptionBasic(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId){
        WorthyConsumptionResponseDto.ReadBasicResponse WorthyConsumptionResponseDto = worthyConsumptionService.readBasicWorthyConsumption(worthyConsumptionId);
        return new ResponseEntity<>(WorthyConsumptionResponseDto, HttpStatus.OK);
    }
    @Operation(summary = "가치소비 상세 조회", description = "가치소비의 상세 내용을 조회합니다.")
    @GetMapping("/{worthyConsumptionId}/detail")
    public ResponseEntity<WorthyConsumptionResponseDto.ReadDetailResponse> getWorthyConsumptionDetail(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId){
        WorthyConsumptionResponseDto.ReadDetailResponse WorthyConsumptionResponseDto = worthyConsumptionService.readDetailWorthyConsumption(worthyConsumptionId);
        return new ResponseEntity<>(WorthyConsumptionResponseDto, HttpStatus.OK);
    }
    @Operation(summary = "가치소비 전체 조회", description = "전체 가치소비를 조회합니다.")
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
    @Operation(summary = "가치소비 설명 수정", description = "가치소비의 설명을 수정합니다.")
    @PutMapping("/{worthyConsumptionId}/content")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionContent(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @Valid @RequestBody WorthyConsumptionRequestDto.UpdateContentRequest worthyConsumptionRequestDto){
        worthyConsumptionValidationService.isSameWorthyConsumptionId(worthyConsumptionId, worthyConsumptionRequestDto.getId());
        worthyConsumptionService.updateContentWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "가치소비의 미디어 url 수정", description = "가치소비의 이미지 및 영상의 url을 수정합니다.")
    @PutMapping("/{worthyConsumptionId}/url")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionUrl(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @Valid @RequestBody WorthyConsumptionRequestDto.UpdateUrlRequest worthyConsumptionRequestDto){
        worthyConsumptionValidationService.isSameWorthyConsumptionId(worthyConsumptionId, worthyConsumptionRequestDto.getId());
        worthyConsumptionService.updateUrlWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "가치소비 가격 관련 수정", description = "가치소비의 가격과 가격 태그를 수정합니다.")
    @PutMapping("/{worthyConsumptionId}/price")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionPrice(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @Valid @RequestBody WorthyConsumptionRequestDto.UpdatePriceRequest worthyConsumptionRequestDto){
        worthyConsumptionValidationService.isSameWorthyConsumptionId(worthyConsumptionId, worthyConsumptionRequestDto.getId());
        worthyConsumptionService.updatePriceWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "가치소비 장소 관련 수정", description = "가치소비의 장소과 장소 태그를 수정합니다.")
    @PutMapping("/{worthyConsumptionId}/place")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionPlace(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @Valid @RequestBody WorthyConsumptionRequestDto.UpdatePlaceRequest worthyConsumptionRequestDto){
        worthyConsumptionValidationService.isSameWorthyConsumptionId(worthyConsumptionId, worthyConsumptionRequestDto.getId());
        worthyConsumptionService.updatePlaceWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "가치소비 날짜 수정", description = "가치소비의 쿠폰 발급 가능 기간을 수정합니다.")
    @PutMapping("/{worthyConsumptionId}/date")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionDate(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @Valid @RequestBody WorthyConsumptionConditionDto.UpdateIssuableCouponDateRequest worthyConsumptionRequestDto){
        worthyConsumptionValidationService.isSameWorthyConsumptionId(worthyConsumptionId, worthyConsumptionRequestDto.getId());
        worthyConsumptionService.updateIssuableCouponDate(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "가치소비 조건 수정", description = "가치소비의 쿠폰 발급시 조건을 수정합니다.")
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
    @Operation(summary = "가치소비 삭제", description = "가치소비를 삭제합니다.")
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
    @Operation(summary = "가치소비의 쿠폰 등록", description = "가치소비의 쿠폰을 등록합니다.")
    @PostMapping("/{worthyConsumptionId}")
    public ResponseEntity<HttpStatus> createCoupon(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @Valid @RequestBody CouponRequestDto.CreationRequest couponRequestDto){
        couponService.createCoupon(worthyConsumptionId, couponRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
