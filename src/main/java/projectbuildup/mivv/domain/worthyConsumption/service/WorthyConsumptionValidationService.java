package projectbuildup.mivv.domain.worthyConsumption.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.couponIssuance.repository.CouponIssuanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.domain.worthyConsumption.repository.WorthyConsumptionRepository;
import projectbuildup.mivv.global.error.exception.CBadRequestException;
import projectbuildup.mivv.global.error.exception.CWorthyConsumptionNotFoundException;

import java.time.LocalDate;

import static org.apache.commons.lang3.ObjectUtils.compare;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorthyConsumptionValidationService {
    private final WorthyConsumptionRepository worthyConsumptionRepository;
    private final CouponIssuanceRepository couponIssuanceRepository;

    /**
     * Controller에서 PathVariable로 받은 가치소비의 Id와 RequestDto속 Id가 같은지 확인하는 로직입니다.
     * @param worthyConsumptionId
     * @param worthyConsumptionDtoId
     */
    public void isSameWorthyConsumptionId(Long worthyConsumptionId, Long worthyConsumptionDtoId){
        if(!ObjectUtils.equals(worthyConsumptionId, worthyConsumptionDtoId))//이거 처리가 맞는지.. Long은 객체고 long은 원시형인데 equals하면 같은 값이어도 다르게 취급되는거 아닌가 싶네..
            throw new CBadRequestException("PathVariable WorthyConsumptionId와 requestDto 속 가치소비 id가 일치하지 않습니다.");
    }

}
