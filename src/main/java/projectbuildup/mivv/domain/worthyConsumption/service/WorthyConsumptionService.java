package projectbuildup.mivv.domain.worthyConsumption.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionRequestDto;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionUrlRequestDto;
import projectbuildup.mivv.domain.worthyConsumption.dto.response.WorthyConsumptionResponseDto;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumptionUrl;
import projectbuildup.mivv.domain.worthyConsumption.repository.WorthyConsumptionRepository;
import projectbuildup.mivv.domain.worthyConsumption.repository.WorthyConsumptionUrlRepository;
import projectbuildup.mivv.global.error.exception.CWorthyConsumptionNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorthyConsumptionService {
    private final WorthyConsumptionRepository worthyConsumptionRepository;
    public void createWorthyConsumption(WorthyConsumptionRequestDto.CreationRequest worthyConsumptionRequestDto){
        WorthyConsumptionUrl worthyConsumptionUrl = new WorthyConsumptionUrl(worthyConsumptionRequestDto.getWorthyConsumptionUrlRequest());
        WorthyConsumption worthyConsumption = worthyConsumptionRequestDto.toEntity(worthyConsumptionUrl);
        worthyConsumptionRepository.save(worthyConsumption);
    }
    public WorthyConsumptionResponseDto.ReadSummaryResponse readSummaryWorthyConsumption(Long worthyConsumptionId){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException:: new);//Error 추가하기 -> validationService 안쓰는게 맞다.
        return new WorthyConsumptionResponseDto.ReadSummaryResponse(worthyConsumption);
    }
    public WorthyConsumptionResponseDto.ReadBasicResponse readBasicWorthyConsumption(Long worthyConsumptionId){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException:: new);
        return new WorthyConsumptionResponseDto.ReadBasicResponse(worthyConsumption);
    }
    public WorthyConsumptionResponseDto.ReadDetailResponse readDetailWorthyConsumption(Long worthyConsumptionId){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException:: new);
        return new WorthyConsumptionResponseDto.ReadDetailResponse(worthyConsumption);
    }
    public void updateContentWorthyConsumption(WorthyConsumptionRequestDto.UpdateContentRequest worthyConsumptionRequestDto){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionRequestDto.getId()).orElseThrow(CWorthyConsumptionNotFoundException:: new);
        worthyConsumption.updateContent(worthyConsumptionRequestDto);
        worthyConsumptionRepository.save(worthyConsumption);
    }
    public void updateUrlWorthyConsumption(WorthyConsumptionRequestDto.UpdateUrlRequest worthyConsumptionRequestDto){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionRequestDto.getId()).orElseThrow(CWorthyConsumptionNotFoundException:: new);
        worthyConsumption.getWorthyConsumptionUrl().updateUrl(worthyConsumptionRequestDto);
        worthyConsumptionRepository.save(worthyConsumption);
    }
    public void updatePriceWorthyConsumption(WorthyConsumptionRequestDto.UpdatePriceRequest worthyConsumptionRequestDto){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionRequestDto.getId()).orElseThrow(CWorthyConsumptionNotFoundException:: new);
        worthyConsumption.updatePrice(worthyConsumptionRequestDto);
        worthyConsumptionRepository.save(worthyConsumption);
    }
    public void updatePlaceWorthyConsumption(WorthyConsumptionRequestDto.UpdatePlaceRequest worthyConsumptionRequestDto){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionRequestDto.getId()).orElseThrow(CWorthyConsumptionNotFoundException:: new);
        worthyConsumption.updatePlace(worthyConsumptionRequestDto);
        worthyConsumptionRepository.save(worthyConsumption);
    }
    public void deleteWorthyConsumption(Long worthyConsumptionId){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException:: new);
        worthyConsumptionRepository.delete(worthyConsumption);
    }
}
