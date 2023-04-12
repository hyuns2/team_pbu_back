package projectbuildup.mivv.domain.worthyConsumption.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.worthyConsumption.repository.WorthyConsumptionRepository;
import projectbuildup.mivv.global.error.exception.CBadRequestException;
import projectbuildup.mivv.global.error.exception.CWorthyConsumptionNotFoundException;

import static org.apache.commons.lang3.ObjectUtils.compare;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorthyConsumptionValidationService {
    private final WorthyConsumptionRepository worthyConsumptionRepository;
    public void isSameWorthyConsumptionId(Long worthyConsumptionId, Long worthyConsumptionDtoId){
        if(!ObjectUtils.equals(worthyConsumptionId, worthyConsumptionDtoId))
            throw new CBadRequestException("requestId와 requestDto 속 가치소비 id가 일치하지 않습니다.");
    }


}
