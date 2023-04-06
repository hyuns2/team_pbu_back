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
            return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/{worthyConsumptionId}/summary")//다른 스타일로 짜보려고 노력함
    public ResponseEntity<WorthyConsumptionResponseDto.ReadSummaryResponse> getWorthyConsumptionSummary(@PathVariable(name = "worthyConsumptionId") WorthyConsumptionRequestDto.IdRequest worthyConsumptionRequestDto){
        WorthyConsumptionResponseDto.ReadSummaryResponse worthyConsumptionResponseDto = worthyConsumptionService.readSummaryWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(worthyConsumptionResponseDto, HttpStatus.OK);
    }
    @GetMapping("/{worthyConsumptionId}/basic")//다른 스타일로 짜보려고 노력함
    public ResponseEntity<WorthyConsumptionResponseDto.ReadBasicResponse> getWorthyConsumptionBasic(@PathVariable(name = "worthyConsumptionId") WorthyConsumptionRequestDto.IdRequest worthyConsumptionRequestDto){
        WorthyConsumptionResponseDto.ReadBasicResponse worthyConsumptionResponseDto = worthyConsumptionService.readBasicWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(worthyConsumptionResponseDto, HttpStatus.OK);
    }
    @GetMapping("/{worthyConsumptionId}/detail")//다른 스타일로 짜보려고 노력함
    public ResponseEntity<WorthyConsumptionResponseDto.ReadDetailResponse> getWorthyConsumptionDetail(@PathVariable(name = "worthyConsumptionId") WorthyConsumptionRequestDto.IdRequest worthyConsumptionRequestDto){
        WorthyConsumptionResponseDto.ReadDetailResponse worthyConsumptionResponseDto = worthyConsumptionService.readDetailWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(worthyConsumptionResponseDto, HttpStatus.OK);
    }
    @GetMapping("/{worthyConsumptionId}/summary/with_id")//다른 스타일로 짜보려고 노력함
    public ResponseEntity<WorthyConsumptionResponseDto.ReadSummaryResponse> getWorthyConsumptionSummary(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId){
        //WorthyConsumptionRequestDto.ReadSummaryRequest WorthyConsumptionRequestDto = (WorthyConsumptionRequestDto.ReadSummaryRequest) new WorthyConsumptionRequestDto.IdRequest(worthyConsumptionId);
        WorthyConsumptionRequestDto.ReadSummaryRequest worthyConsumptionRequestDto = new WorthyConsumptionRequestDto.ReadSummaryRequest();
        worthyConsumptionRequestDto.setId(worthyConsumptionId);

        WorthyConsumptionResponseDto.ReadSummaryResponse WorthyConsumptionResponseDto = worthyConsumptionService.readSummaryWorthyConsumption(WorthyConsumptionRequestDto);
        return new ResponseEntity<>(WorthyConsumptionResponseDto, HttpStatus.OK);
    }
    @GetMapping("/{worthyConsumptionId}/basic/with_id")//다른 스타일로 짜보려고 노력함
    public ResponseEntity<WorthyConsumptionResponseDto.ReadBasicResponse> getWorthyConsumptionBasic(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId){
        WorthyConsumptionRequestDto.ReadBasicRequest WorthyConsumptionRequestDto = (WorthyConsumptionRequestDto.ReadBasicRequest) new WorthyConsumptionRequestDto.IdRequest(worthyConsumptionId);
        WorthyConsumptionResponseDto.ReadBasicResponse WorthyConsumptionResponseDto = worthyConsumptionService.readBasicWorthyConsumption(WorthyConsumptionRequestDto);
        return new ResponseEntity<>(WorthyConsumptionResponseDto, HttpStatus.OK);
    }
    @GetMapping("/{worthyConsumptionId}/detail/with_id")//다른 스타일로 짜보려고 노력함
    public ResponseEntity<WorthyConsumptionResponseDto.ReadDetailResponse> getWorthyConsumptionDetail(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId){
        WorthyConsumptionRequestDto.ReadDetailRequest WorthyConsumptionRequestDto = (WorthyConsumptionRequestDto.ReadDetailRequest) new WorthyConsumptionRequestDto.IdRequest(worthyConsumptionId);
        WorthyConsumptionResponseDto.ReadDetailResponse WorthyConsumptionResponseDto = worthyConsumptionService.readDetailWorthyConsumption(WorthyConsumptionRequestDto);
        return new ResponseEntity<>(WorthyConsumptionResponseDto, HttpStatus.OK);
    }
//    @GetMapping("/{WorthyConsumptionId}/summary")
//    public ResponseEntity<WorthyConsumptionResponseDto.ReadSummaryResponse> getWorthyConsumptionSummary(@PathVariable(name = "WorthyConsumptionId") Long WorthyConsumptionId){
//        WorthyConsumptionRequestDto.ReadSummaryRequest WorthyConsumptionRequestDto = new WorthyConsumptionRequestDto. new WorthyConsumptionRequestDto.ReadSummaryRequest(WorthyConsumptionId);
//        WorthyConsumptionResponseDto.ReadSummaryResponse WorthyConsumptionResponseDto = WorthyConsumptionService.readSummaryWorthyConsumption(WorthyConsumptionRequestDto);
//        return new ResponseEntity<>(WorthyConsumptionResponseDto, HttpStatus.OK);
//    }
    @PutMapping("/{worthyConsumptionId}/update/content")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionContent(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @RequestBody WorthyConsumptionRequestDto.UpdateContentRequest worthyConsumptionRequestDto){
        //pathVariable과 dto의 id값이 동일한가 valid 검사 해야 함
        worthyConsumptionService.updateContentWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{worthyConsumptionId}/update/url")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionUrl(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @RequestBody WorthyConsumptionRequestDto.UpdateUrlRequest worthyConsumptionRequestDto){
        //pathVariable과 dto의 id값이 동일한가 valid 검사 해야 함
        worthyConsumptionService.updateUrlWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{worthyConsumptionId}/update/price")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionPrice(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @RequestBody WorthyConsumptionRequestDto.UpdatePriceRequest worthyConsumptionRequestDto){
        //pathVariable과 dto의 id값이 동일한가 valid 검사 해야 함
        worthyConsumptionService.updatePriceWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{worthyConsumptionId}/update/place")
    public ResponseEntity<HttpStatus> updateWorthyConsumptionPlace(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @RequestBody WorthyConsumptionRequestDto.UpdatePlaceRequest worthyConsumptionRequestDto){
        //pathVariable과 dto의 id값이 동일한가 valid 검사 해야 함
        worthyConsumptionService.updatePlaceWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{worthyConsumptionId}")
    public ResponseEntity<HttpStatus> deleteWorthyConsumption(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId){
        //pathVariable과 dto의 id값이 동일한가 valid 검사 해야 함
        WorthyConsumptionRequestDto.DeleteRequest worthyConsumptionRequestDto = (WorthyConsumptionRequestDto.DeleteRequest) new WorthyConsumptionRequestDto.IdRequest(worthyConsumptionId);
        worthyConsumptionService.deleteWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /*@DeleteMapping("/{worthyConsumptionId}")
    public ResponseEntity<HttpStatus> deleteWorthyConsumption(@PathVariable(name = "worthyConsumptionId") WorthyConsumptionRequestDto.DeleteRequest worthyConsumptionRequestDto){
        //pathVariable과 dto의 id값이 동일한가 valid 검사 해야 함
        worthyConsumptionService.deleteWorthyConsumption(worthyConsumptionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

}
