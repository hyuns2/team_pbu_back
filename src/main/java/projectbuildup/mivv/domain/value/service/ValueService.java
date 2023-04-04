package projectbuildup.mivv.domain.value.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.value.dto.request.ValueRequestDto;
import projectbuildup.mivv.domain.value.dto.response.ValueResponseDto;
import projectbuildup.mivv.domain.value.entity.Value;
import projectbuildup.mivv.domain.value.repository.ValueRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValueService {
    private final ValueRepository valueRepository;
    public void createValue(ValueRequestDto.CreationRequest valueRequestDto){
        Value value = valueRequestDto.toEntity();
        valueRepository.save(value);
    }
    public ValueResponseDto.ReadSummaryResponse readSummaryValue(ValueRequestDto.IdRequest valueRequestDto){
        Value value = valueRepository.findById(valueRequestDto.getId()).orElseThrow();
        return new ValueResponseDto.ReadSummaryResponse(value);
    }
    public ValueResponseDto.ReadBasicResponse readBasicValue(ValueRequestDto.IdRequest valueRequestDto){
        Value value = valueRepository.findById(valueRequestDto.getId()).orElseThrow();
        return new ValueResponseDto.ReadBasicResponse(value);
    }
    public ValueResponseDto.ReadDetailResponse readDetailValue(ValueRequestDto.IdRequest valueRequestDto){
        Value value = valueRepository.findById(valueRequestDto.getId()).orElseThrow();
        return new ValueResponseDto.ReadDetailResponse(value);
    }
    public void updateContentValue(ValueRequestDto.UpdateContentRequest valueRequestDto){
        Value value = valueRepository.findById(valueRequestDto.getId()).orElseThrow();
        value.updateContent(valueRequestDto);
        valueRepository.save(value);
    }
    public void updateUrlValue(ValueRequestDto.UpdateUrlRequest valueRequestDto){
        Value value = valueRepository.findById(valueRequestDto.getId()).orElseThrow();
        value.getValueUrl().updateUrl(valueRequestDto);
        valueRepository.save(value);
    }
    public void updatePriceValue(ValueRequestDto.UpdatePriceRequest valueRequestDto){
        Value value = valueRepository.findById(valueRequestDto.getId()).orElseThrow();
        value.updatePrice(valueRequestDto);
        valueRepository.save(value);
    }
    public void updatePlaceValue(ValueRequestDto.UpdatePlaceRequest valueRequestDto){
        Value value = valueRepository.findById(valueRequestDto.getId()).orElseThrow();
        value.updatePlace(valueRequestDto);
        valueRepository.save(value);
    }
    public void deleteValue(ValueRequestDto.DeleteRequest valueRequestDto){
        Value value = valueRepository.findById(valueRequestDto.getId()).orElseThrow();
        valueRepository.delete(value);
    }
}
