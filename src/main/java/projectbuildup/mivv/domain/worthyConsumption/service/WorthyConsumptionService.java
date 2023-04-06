package projectbuildup.mivv.domain.worthyConsumption.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionRequestDto;
import projectbuildup.mivv.domain.worthyConsumption.dto.response.WorthyConsumptionResponseDto;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.domain.worthyConsumption.repository.WorthyConsumptionRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorthyConsumptionService {
    private final WorthyConsumptionRepository worthyConsumptionRepository;
    public void createWorthyConsumption(WorthyConsumptionRequestDto.CreationRequest worthyConsumptionRequestDto){
        WorthyConsumption worthyConsumption = worthyConsumptionRequestDto.toEntity();
        worthyConsumptionRepository.save(worthyConsumption);
    }
    public WorthyConsumptionResponseDto.ReadSummaryResponse readSummaryWorthyConsumption(WorthyConsumptionRequestDto.IdRequest worthyConsumptionRequestDto){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionRequestDto.getId()).orElseThrow();
        return new WorthyConsumptionResponseDto.ReadSummaryResponse(worthyConsumption);
    }
    public WorthyConsumptionResponseDto.ReadBasicResponse readBasicWorthyConsumption(WorthyConsumptionRequestDto.IdRequest worthyConsumptionRequestDto){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionRequestDto.getId()).orElseThrow();
        return new WorthyConsumptionResponseDto.ReadBasicResponse(worthyConsumption);
    }
    public WorthyConsumptionResponseDto.ReadDetailResponse readDetailWorthyConsumption(WorthyConsumptionRequestDto.IdRequest worthyConsumptionRequestDto){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionRequestDto.getId()).orElseThrow();
        return new WorthyConsumptionResponseDto.ReadDetailResponse(worthyConsumption);
    }
    public void updateContentWorthyConsumption(WorthyConsumptionRequestDto.UpdateContentRequest worthyConsumptionRequestDto){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionRequestDto.getId()).orElseThrow();
        worthyConsumption.updateContent(worthyConsumptionRequestDto);
        worthyConsumptionRepository.save(worthyConsumption);
    }
    public void updateUrlWorthyConsumption(WorthyConsumptionRequestDto.UpdateUrlRequest worthyConsumptionRequestDto){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionRequestDto.getId()).orElseThrow();
        worthyConsumption.getWorthyConsumptionUrl().updateUrl(worthyConsumptionRequestDto);
        worthyConsumptionRepository.save(worthyConsumption);
    }
    public void updatePriceWorthyConsumption(WorthyConsumptionRequestDto.UpdatePriceRequest worthyConsumptionRequestDto){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionRequestDto.getId()).orElseThrow();
        worthyConsumption.updatePrice(worthyConsumptionRequestDto);
        worthyConsumptionRepository.save(worthyConsumption);
    }
    public void updatePlaceWorthyConsumption(WorthyConsumptionRequestDto.UpdatePlaceRequest worthyConsumptionRequestDto){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionRequestDto.getId()).orElseThrow();
        worthyConsumption.updatePlace(worthyConsumptionRequestDto);
        worthyConsumptionRepository.save(worthyConsumption);
    }
    public void deleteWorthyConsumption(WorthyConsumptionRequestDto.DeleteRequest worthyConsumptionRequestDto){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionRequestDto.getId()).orElseThrow();
        worthyConsumptionRepository.delete(worthyConsumption);
    }
}
