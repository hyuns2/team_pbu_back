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
    /*
     * pathVariable로 받은 id값과 RequestBody로 받은 DTO안의 id값을 비교해 같으면 OK
     *
     * 초록색 주석은 자바독. 다른 사람에게 내가 짠 코드가 뭔지 설명하기 위해
     */
    private final WorthyConsumptionRepository worthyConsumptionRepository;

    /**
     *
     * @param worthyConsumptionId
     * @param worthyConsumptionIdIdDto
     */
    public void isSameWorthyConsumptionId(Long worthyConsumptionId, Long worthyConsumptionIdIdDto){
        if(!ObjectUtils.equals(worthyConsumptionId, worthyConsumptionIdIdDto))
            throw new CBadRequestException("requestId와 requestDto 속 가치소비 id가 일치하지 않습니다.");
        else
            isRealWorthyConsumptionId(worthyConsumptionId);
    }
    public void isRealWorthyConsumptionId(Long worthyConsumptionId){
        if(worthyConsumptionRepository.findById(worthyConsumptionId).isEmpty())
            throw new CWorthyConsumptionNotFoundException();
    }

}
