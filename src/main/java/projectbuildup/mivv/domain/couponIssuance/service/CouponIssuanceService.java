package projectbuildup.mivv.domain.couponIssuance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.coupon.dto.response.CouponResponseDto;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.coupon.repository.CouponRepository;
import projectbuildup.mivv.domain.couponIssuance.dto.CouponIssuanceDto;
import projectbuildup.mivv.domain.couponIssuance.entity.CouponIssuance;
import projectbuildup.mivv.domain.couponIssuance.repository.CouponIssuanceRepository;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.remittance.service.RemittanceService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CBadRequestException;
import projectbuildup.mivv.global.error.exception.CCouponNotFoundException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponIssuanceService {
    private final CouponIssuanceRepository couponIssuanceRepository;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final RemittanceRepository remittanceRepository;
    /*
     * 발급받을때 고려할 사항
     * 1. 먼저 유저가 유효한 유저인지 판단 (유저가 진짜 유저인가) : O
     * 2. 쿠폰이 유효한지 판단 (쿠폰이 진짜 있는지 : O, 발급 가능한 날짜인지 : O -> 이건 가치소비 단에서 해결함)
     * 3. 유저가 발급 받을 수 있는 조건인지 (이미 보유하고 있는 쿠폰인지 : O, 전월 달성 금액 조건을 충족했는지)
     *
     * 사용할때 고려할 사항
     * 1. 유저가 유효한 유저인지 판단 (유저가 진짜 유저인가)
     * 2. 유저가 보유한 쿠폰이 맞는지 판단
     * 3. 사용가능한 쿠폰인지 판단 (발행은 했고 미사용 상태인지 확인, 날짜 확인)
     * 4. 핀번호 확인
     * 5. 사용시에, isUsed == true로 바꾸기
     */
    /**
     * 사용자가 쿠폰을 발급 받을때 필요한 로직입니다.
     * @param userId
     * @param couponId
     */
    public void issueCoupon(Long userId, Long couponId){
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);

        isIssuable(user, coupon);
        isAchievedLastAmount(user, coupon);
        issue(user, coupon);
    }
    /**
     * 사용자가 쿠폰을 보유하고 있는지 검증하는 로직입니다.
     * @param user
     * @param coupon
     */
    private void isIssuable(User user, Coupon coupon){
        if(couponIssuanceRepository.findByUserAndCoupon(user, coupon).isPresent()){
            throw new CBadRequestException("이미 보유중인 쿠폰입니다.");
        }
    }
    private void isAchievedLastAmount(User user, Coupon coupon){
        long userLastSumAmount = remittanceRepository.findSumAmountByUser(user);
        log.info("사용자 전월 총 금액 {}", userLastSumAmount);

        long limitLastSumAmount = coupon.getWorthyConsumption().getCondition().getLastMonthAmount();
        log.info("전월 한도 금액 {}", limitLastSumAmount);
        if(userLastSumAmount < limitLastSumAmount)
            throw new CBadRequestException("전월 달성 금액 미달입니다.");

    }
    /**
     * 사용자가 쿠폰을 발급합니다.
     * @param user
     * @param coupon
     */
    private void issue(User user, Coupon coupon){
        CouponIssuance couponIssuance = new CouponIssuance(user, coupon);
        couponIssuanceRepository.save(couponIssuance);
    }
    /**
     * 사용자가 사용 가능한 쿠폰 리스트를 조회할 때 필요한 로직입니다.
     * @param
     * @return
     */
    public List<CouponResponseDto.ReadResponseWithWorthyConsumption> getUsableCouponList(Long userId){
        userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);

        List<CouponIssuance> couponIssuances = couponIssuanceRepository.findAllByUserId(userId).stream().toList();
        log.info("사용자가 발급한 쿠폰 개수 {}", couponIssuances.stream().count());

        List<Coupon> coupons = couponIssuances.stream()
                .filter(couponIssuance ->
                        ((couponIssuance.isCreated()==true)&&(couponIssuance.isUsed()==false)))
                .map(CouponIssuance::getCoupon)
                .collect(Collectors.toList());
        log.info("사용자가 발급한 쿠폰 중 미사용 쿠폰 개수 {}", coupons.stream().count());

        return coupons.stream()
                .map(CouponResponseDto.ReadResponseWithWorthyConsumption::new)
                .collect(Collectors.toList());
    }

    /**
     * 사용자가 사용완료한 쿠폰을 모두 조회하는 로직입니다.
     * @param
     * @return
     */
    public List<CouponResponseDto.ReadResponseWithWorthyConsumption> getUsedCouponList(Long userId){
        userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);

        List<CouponIssuance> couponIssuances = couponIssuanceRepository.findAllByUserId(userId).stream().toList();
        List<Coupon> coupons = couponIssuances.stream()
                .filter(couponIssuance ->
                        ((couponIssuance.isCreated()==true)&&(couponIssuance.isUsed()==true)))
                .map(CouponIssuance::getCoupon)
                .collect(Collectors.toList());
        log.info("사용자가 발급한 쿠폰 중 사용 완료 쿠폰 개수 {}", coupons.stream().count());

        return coupons.stream()
                .map(CouponResponseDto.ReadResponseWithWorthyConsumption::new)
                .collect(Collectors.toList());
    }
    /***
     * 사용자가 쿠폰을 사용할 때 필요한 로직입니다.
     * @param couponId
     * @param userId
     */
    public void useCouponByUser(Long couponId, Long userId, CouponIssuanceDto.PinDto pinDto){
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);
        CouponIssuance couponIssuance = couponIssuanceRepository.findByUserIdAndCouponId(userId, couponId);

        isUsableCoupon(couponIssuance);
        isUsableCouponDate(couponIssuance.getCoupon());
        isCorrectPinNumber(coupon, pinDto.getPin());

        couponIssuance.useCoupon();

        couponIssuanceRepository.save(couponIssuance);
    }
    /**
     * 사용자가 보유한 쿠폰이 유효한지 검증하는 로직입니다.
     * 발급이 되었고 사용하기 전인 쿠폰인지 확인합니다.
     * 두가지 로직으로 짜봤습니다.
     * @param couponIssuance
     * @return
     */
    /*public Boolean isUsable(CouponIssuance couponIssuance){
        if((couponIssuance.getIsCreated()==true) && (couponIssuance.getIsUsed() == false))
            return true;
        else
            return false;

    }*/
    private void isUsableCoupon(CouponIssuance couponIssuance){
        if(! ((couponIssuance.isCreated()==true)
                && (couponIssuance.isUsed()==false)) )
            throw new CBadRequestException("사용 불가능한 쿠폰입니다.");
    }
    private void isUsableCouponDate(Coupon coupon){
        /*if(! ((coupon.getLimitStartDate().isBefore(LocalDate.now())) || coupon.getLimitStartDate().isEqual(LocalDate.now())
                && (coupon.getLimitEndDate().isAfter(LocalDate.now())) || coupon.getLimitStartDate().isEqual(LocalDate.now())
        ))
            throw new CBadRequestException("사용 가능한 날짜가 지난 쿠폰입니다.");*/
        if(coupon.getLimitStartDate().isAfter(LocalDate.now())||coupon.getLimitEndDate().isBefore(LocalDate.now()))
            throw new CBadRequestException("사용 가능한 날짜가 지난 쿠폰입니다.");
    }
    /**
     * 쿠폰 핀 번호와 사용자가 입력한 핀 번호가 일치한지 판단하는 로직입니다.
     * @param coupon
     * @param pin
     */
    private void isCorrectPinNumber(Coupon coupon, int pin){

        log.info("쿠폰의 핀 번호 {} 입력받은 핀 번호 {}", coupon.getPin(), pin);

        if(coupon.getPin() != pin)
            throw new CBadRequestException("핀 번호가 일치하지 않습니다.");
    }
}
