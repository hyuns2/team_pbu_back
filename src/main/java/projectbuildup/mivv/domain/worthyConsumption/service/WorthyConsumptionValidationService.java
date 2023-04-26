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
        if(!ObjectUtils.equals(worthyConsumptionId, worthyConsumptionDtoId))
            throw new CBadRequestException("PathVariable WorthyConsumptionId와 requestDto 속 가치소비 id가 일치하지 않습니다.");
    }

}
