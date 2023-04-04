package projectbuildup.mivv.domain.value.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.value.dto.request.ValueRequestDto;
import projectbuildup.mivv.domain.value.dto.response.ValueResponseDto;
import projectbuildup.mivv.domain.value.service.ValueService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/values")
@Tag(name = "[Value]", description = "가치소비 관련 API입니다.")
public class ValueController {
    private final ValueService valueService;
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createValue(ValueRequestDto.CreationRequest valueRequestDto){
            valueService.createValue(valueRequestDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/{valueId}")//다른 스타일로 짜보려고 노력함
    public ResponseEntity<ValueResponseDto.ReadSummaryResponse> getValueSummary(@PathVariable(name = "valueId") ValueRequestDto.IdRequest valueRequestDto){
        ValueResponseDto.ReadSummaryResponse valueResponseDto = valueService.readSummaryValue(valueRequestDto);
        return new ResponseEntity<>(valueResponseDto, HttpStatus.OK);
    }
    @GetMapping("/{valueId}/basic")//다른 스타일로 짜보려고 노력함
    public ResponseEntity<ValueResponseDto.ReadBasicResponse> getValueBasic(@PathVariable(name = "valueId") ValueRequestDto.IdRequest valueRequestDto){
        ValueResponseDto.ReadBasicResponse valueResponseDto = valueService.readBasicValue(valueRequestDto);
        return new ResponseEntity<>(valueResponseDto, HttpStatus.OK);
    }
    @GetMapping("/{valueId}/detail")//다른 스타일로 짜보려고 노력함
    public ResponseEntity<ValueResponseDto.ReadDetailResponse> getValueDetail(@PathVariable(name = "valueId") ValueRequestDto.IdRequest valueRequestDto){
        ValueResponseDto.ReadDetailResponse valueResponseDto = valueService.readDetailValue(valueRequestDto);
        return new ResponseEntity<>(valueResponseDto, HttpStatus.OK);
    }
    @GetMapping("/{valueId}/basic")//다른 스타일로 짜보려고 노력함
    public ResponseEntity<ValueResponseDto.ReadSummaryResponse> getValueSummary(@PathVariable(name = "valueId") Long valueId){
        ValueRequestDto.ReadSummaryRequest valueRequestDto = (ValueRequestDto.ReadSummaryRequest) new ValueRequestDto.IdRequest(valueId);
        ValueResponseDto.ReadSummaryResponse valueResponseDto = valueService.readSummaryValue(valueRequestDto);
        return new ResponseEntity<>(valueResponseDto, HttpStatus.OK);
    }
    @GetMapping("/{valueId}/basic")//다른 스타일로 짜보려고 노력함
    public ResponseEntity<ValueResponseDto.ReadBasicResponse> getValueBasic(@PathVariable(name = "valueId") Long valueId){
        ValueRequestDto.ReadBasicRequest valueRequestDto = (ValueRequestDto.ReadBasicRequest) new ValueRequestDto.IdRequest(valueId);
        ValueResponseDto.ReadBasicResponse valueResponseDto = valueService.readBasicValue(valueRequestDto);
        return new ResponseEntity<>(valueResponseDto, HttpStatus.OK);
    }
    @GetMapping("/{valueId}/detail")//다른 스타일로 짜보려고 노력함
    public ResponseEntity<ValueResponseDto.ReadDetailResponse> getValueDetail(@PathVariable(name = "valueId") Long valueId){
        ValueRequestDto.ReadDetailRequest valueRequestDto = (ValueRequestDto.ReadDetailRequest) new ValueRequestDto.IdRequest(valueId);
        ValueResponseDto.ReadDetailResponse valueResponseDto = valueService.readDetailValue(valueRequestDto);
        return new ResponseEntity<>(valueResponseDto, HttpStatus.OK);
    }
//    @GetMapping("/{valueId}/summary")
//    public ResponseEntity<ValueResponseDto.ReadSummaryResponse> getValueSummary(@PathVariable(name = "valueId") Long valueId){
//        ValueRequestDto.ReadSummaryRequest valueRequestDto = new ValueRequestDto. new ValueRequestDto.ReadSummaryRequest(valueId);
//        ValueResponseDto.ReadSummaryResponse valueResponseDto = valueService.readSummaryValue(valueRequestDto);
//        return new ResponseEntity<>(valueResponseDto, HttpStatus.OK);
//    }
    @PutMapping("/{valueId}")
    public ResponseEntity<HttpStatus> updateValueContent(@PathVariable(name = "valueId") Long valueId, @RequestBody ValueRequestDto.UpdateContentRequest valueRequestDto){
        //pathVariable과 dto의 id값이 동일한가 valid 검사 해야 함
        valueService.updateContentValue(valueRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{valueId}")
    public ResponseEntity<HttpStatus> updateUrlContent(@PathVariable(name = "valueId") Long valueId, @RequestBody ValueRequestDto.UpdateUrlRequest valueRequestDto){
        //pathVariable과 dto의 id값이 동일한가 valid 검사 해야 함
        valueService.updateUrlValue(valueRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{valueId}")
    public ResponseEntity<HttpStatus> updatePriceContent(@PathVariable(name = "valueId") Long valueId, @RequestBody ValueRequestDto.UpdatePriceRequest valueRequestDto){
        //pathVariable과 dto의 id값이 동일한가 valid 검사 해야 함
        valueService.updatePriceValue(valueRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{valueId}")
    public ResponseEntity<HttpStatus> updatePlaceContent(@PathVariable(name = "valueId") Long valueId, @RequestBody ValueRequestDto.UpdatePlaceRequest valueRequestDto){
        //pathVariable과 dto의 id값이 동일한가 valid 검사 해야 함
        valueService.updatePlaceValue(valueRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{valueId}")
    public ResponseEntity<HttpStatus> deleteValue(@PathVariable(name = "valueId") Long valueId){
        //pathVariable과 dto의 id값이 동일한가 valid 검사 해야 함
        ValueRequestDto.DeleteRequest valueRequestDto = (ValueRequestDto.DeleteRequest) new ValueRequestDto.IdRequest(valueId);
        valueService.deleteValue(valueRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{valueId}")
    public ResponseEntity<HttpStatus> deleteValue(@PathVariable(name = "valueId") ValueRequestDto.DeleteRequest valueRequestDto){
        //pathVariable과 dto의 id값이 동일한가 valid 검사 해야 함
        valueService.deleteValue(valueRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
