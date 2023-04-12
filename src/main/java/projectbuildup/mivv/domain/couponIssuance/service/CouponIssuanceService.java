package projectbuildup.mivv.domain.couponIssuance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.coupon.dto.response.CouponResponseDto;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.coupon.repository.CouponRepository;
import projectbuildup.mivv.domain.couponIssuance.entity.CouponIssuance;
import projectbuildup.mivv.domain.couponIssuance.repository.CouponIssuanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CBadRequestException;
import projectbuildup.mivv.global.error.exception.CCouponNotFoundException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CouponIssuanceService {
    private final CouponIssuanceRepository couponIssuanceRepository;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;

    /**
     * 사용자가 쿠폰을 발급 받을때 필요한 로직입니다.
     * @param userId
     * @param couponId
     */
    public void getCoupon(Long userId, Long couponId){
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);
        isIssuable(user, coupon);
        issue(user, coupon);
    }

    /**
     * 사용자가 쿠폰을 보유하고 있는지 검증하는 로직입니다.
     * @param user
     * @param coupon
     */
    public void isIssuable(User user, Coupon coupon){
        if(couponIssuanceRepository.findByUserAndCoupon(user, coupon).isPresent()){
            throw new CBadRequestException("이미 보유중인 쿠폰입니다.");
        }
    }

    /**
     * 사용자가 쿠폰을 발급합니다.
     * @param user
     * @param coupon
     */
    public void issue(User user, Coupon coupon){
        CouponIssuance couponIssuance = new CouponIssuance(user, coupon);
        couponIssuanceRepository.save(couponIssuance);
    }

    /**
     * 사용자가 보유한 쿠폰이 유효한지 검증하는 로직입니다.
     * 발급이 되었고 사용하기 전인 쿠폰인지 확인합니다.
     * @param couponIssuance
     * @return
     */
    public Boolean isUsable(CouponIssuance couponIssuance){
        if((couponIssuance.getIsCreated()==true) && (couponIssuance.getIsUsed() == false))
            return true;
        else
            return false;

    }
    public void isUsableMethod(CouponIssuance couponIssuance){
        if(!(couponIssuance.getIsCreated()==true) && (couponIssuance.getIsUsed() == false))
            throw new CBadRequestException("사용 불가능한 쿠폰입니다.");

    }

    /**
     * 사용자가 사용가능한 쿠폰 리스트를 조회할 때 필요한 로직입니다.
     * @param user
     * @return
     */
    public List<CouponResponseDto.ReadResponseWithWorthyConsumption> getUsableCouponList(User user){
        List<CouponIssuance> couponIssuances = couponIssuanceRepository.findAllByUserId(user.getId()).stream().toList();
        List<Coupon> coupons = new ArrayList<>();
        Iterator iter = couponIssuances.iterator(); //뭔 차이지...

        CouponIssuance couponIssuance;
        while(iter.hasNext()){
            couponIssuance = (CouponIssuance) iter.next();
            if(isUsable(couponIssuance))
                coupons.add(couponIssuance.getCoupon());
        }

        return coupons.stream().map(CouponResponseDto.ReadResponseWithWorthyConsumption::new).collect(Collectors.toList());
    }

    /***
     * 사용자가 쿠폰을 사용할 때 필요한 로직입니다.
     * @param couponId
     * @param user
     */
    public void useCouponByUser(Long couponId, User user){
        CouponIssuance couponIssuance = couponIssuanceRepository.findByUserIdAndCouponId(user.getId(), couponId);
        if(isUsable(couponIssuance))
            couponIssuance.useCoupon();

        couponIssuanceRepository.save(couponIssuance);
    }
}
