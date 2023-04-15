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
    public void checkConditionForIssuingCoupon(Long worthyConsumptionId, User user){

    }

    /**
     * 쿠폰 발급 전, 발급 가능한 인원을 초과하였는지 확인하는 로직입니다.
     * @param couponId
     * @param worthyConsumption
     */
    public void checkMaxParticipants(Long couponId, WorthyConsumption worthyConsumption){
        int nowParticipants = couponIssuanceRepository.countByCouponId(couponId);
        if(nowParticipants>=worthyConsumption.getMaxParticipants())
            throw new CBadRequestException("쿠폰 발급 가능한 인원을 초과하였습니다.");
    }

    /**
     * 쿠폰 발급 전, 발급 가능한 날짜인지 확인하는 로직입니다.
     * @param worthyConsumption
     */
    public void checkDate(WorthyConsumption worthyConsumption){
        LocalDate nowDate = LocalDate.now(); //확인은 더미데이터로 하기
        if( !(worthyConsumption.getCondition().getIssuableCouponStartDate().isBefore(nowDate) && worthyConsumption.getCondition().getIssuableCouponEndDate().isAfter(nowDate)))
            throw new CBadRequestException("발급 가능 날짜가 아닙니다.");
    }


}
