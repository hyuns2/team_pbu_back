package projectbuildup.mivv.domain.worthyConsumption.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionRequestDto;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionUrlRequestDto;
import projectbuildup.mivv.domain.worthyConsumption.dto.response.WorthyConsumptionResponseDto;
import projectbuildup.mivv.domain.worthyConsumption.service.WorthyConsumptionService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/worthyConsumptions")
@Tag(name = "[WorthyConsumption]", description = "가치소비 관련 API입니다.")
public class WorthyConsumptionController {
    private final WorthyConsumptionService worthyConsumptionService;
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createWorthyConsumption(@RequestBody WorthyConsumptionRequestDto.CreationRequest worthyConsumptionRequestDto){
            worthyConsumptionService.createWorthyConsumption(worthyConsumptionRequestDto);
            log.info("worthyConsumptionRequestDto = {}",worthyConsumptionRequestDto);
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
    @PutMapping("/{worthyConsumptionId}/update/content")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionContent(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @RequestBody WorthyConsumptionRequestDto.UpdateContentRequest worthyConsumptionRequestDto){
        worthyConsumptionService.updateContentWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{worthyConsumptionId}/update/url")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionUrl(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @RequestBody WorthyConsumptionRequestDto.UpdateUrlRequest worthyConsumptionRequestDto){
        worthyConsumptionService.updateUrlWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{worthyConsumptionId}/update/price")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionPrice(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @RequestBody WorthyConsumptionRequestDto.UpdatePriceRequest worthyConsumptionRequestDto){
        worthyConsumptionService.updatePriceWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{worthyConsumptionId}/update/place")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionPlace(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @RequestBody WorthyConsumptionRequestDto.UpdatePlaceRequest worthyConsumptionRequestDto){
        worthyConsumptionService.updatePlaceWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{worthyConsumptionId}")
    public ResponseEntity<HttpStatus> deleteWorthyConsumption(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId){
        WorthyConsumptionRequestDto.DeleteRequest worthyConsumptionRequestDto = new WorthyConsumptionRequestDto.DeleteRequest();
        worthyConsumptionRequestDto.setId(worthyConsumptionId);

        worthyConsumptionService.deleteWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
