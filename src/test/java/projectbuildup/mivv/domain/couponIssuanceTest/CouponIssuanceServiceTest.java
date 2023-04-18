package projectbuildup.mivv.domain.couponIssuanceTest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestBody;
import projectbuildup.mivv.common.MockEntity;
import projectbuildup.mivv.domain.auth.service.AuthService;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.coupon.dto.request.CouponRequestDto;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.coupon.repository.CouponRepository;
import projectbuildup.mivv.domain.coupon.service.CouponService;
import projectbuildup.mivv.domain.couponIssuance.dto.CouponIssuanceDto;
import projectbuildup.mivv.domain.couponIssuance.entity.CouponIssuance;
import projectbuildup.mivv.domain.couponIssuance.repository.CouponIssuanceRepository;
import projectbuildup.mivv.domain.couponIssuance.service.CouponIssuanceService;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.participation.repository.ParticipationRepository;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.domain.worthyConsumption.repository.WorthyConsumptionRepository;
import projectbuildup.mivv.domain.worthyConsumption.service.WorthyConsumptionService;
import projectbuildup.mivv.domain.worthyConsumptionTest.WorthyConsumptionServiceTest;
import projectbuildup.mivv.global.error.exception.CCouponNotFoundException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

import java.time.LocalDate;

@SpringBootTest
@Slf4j
public class CouponIssuanceServiceTest extends WorthyConsumptionServiceTest {
    @Autowired
    CouponIssuanceService couponIssuanceService;
    @Autowired
    CouponIssuanceRepository couponIssuanceRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    CouponService couponService;
    @Autowired
    WorthyConsumptionRepository worthyConsumptionRepository;
    @Autowired
    WorthyConsumptionService worthyConsumptionService;
    @Autowired
    ChallengeRepository challengeRepository;
    @Autowired
    ParticipationRepository participationRepository;
    @Autowired
    RemittanceRepository remittanceRepository;

    User user;

    WorthyConsumptionServiceTest worthyConsumptionServiceTest;
    Long couponId1;
    Long couponId2;
    Long couponId3;
    Long couponId4;
    Long couponId5;


    /*

    1. 사용자 생성
    2. 가치소비 생성 -> 쿠폰 생성
    3. 사용자가 쿠폰 발급
    4. 사용자가 쿠폰 사용

     */
    @Test
    @DisplayName("사용자가 쿠폰을 발급하는 테스트")
    void issueCouponByUser(){//확인 완료
        /*
        given

        사용자 생성
        가치소비 생성
        쿠폰 생성

         */
        super.testCreationCouponAtWorthyConsumption();//가치소비 생성, 가치소비에서 쿠폰 생성

        user = MOCK_USER;
        userRepository.save(user);

        Challenge challenge = MOCK_CHALLENGE;
        challengeRepository.save(challenge);

        Participation participation = new Participation(user, challenge);
        participationRepository.save(participation);

        Remittance remittance1 = new Remittance(1000L, participation);
        Remittance remittance2 = new Remittance(2000L, participation);
        Remittance remittance3 = new Remittance(-500L, participation);
        Remittance remittance4 = new Remittance(3000L, participation);

        remittanceRepository.save(remittance1);
        remittanceRepository.save(remittance2);
        remittanceRepository.save(remittance3);
        remittanceRepository.save(remittance4);

        //when : 사용자가 쿠폰을 발급 받을 때
        /*
        userLastSumAmount를 100, limitLastSumAmount를 10으로 두었을 때 통과
         */
        couponId1 = super.couponId1;
        couponId2 = super.couponId2;
        couponId3 = super.couponId3;
        couponId4 = super.couponId4;
        couponId5 = super.couponId5;

        log.info("쿠폰 ID in 사용자 쿠폰 발급 {}", couponId1.longValue());

        couponIssuanceService.issueCoupon(user.getId(), couponId1);

        //then : CouponIssuance 만들어지는지 확인
        CouponIssuance couponIssuance = couponIssuanceRepository.findByUserIdAndCouponId(user.getId(), couponId1);
        log.info("발급 {}",couponIssuance);

        Assertions.assertThat(couponIssuance).isNotNull();

    }
    @Test
    @DisplayName("사용자가 쿠폰 여러개를 발급하는 테스트")
    void issueCouponsByUser(){
        /*
        given
         */
        issueCouponByUser();

        log.info("쿠폰 ID in 사용자 쿠폰 발급 {}", couponId1.longValue());
        log.info("쿠폰 ID in 사용자 쿠폰 발급 {}", couponId2.longValue());
        log.info("쿠폰 ID in 사용자 쿠폰 발급 {}", couponId3.longValue());
        log.info("쿠폰 ID in 사용자 쿠폰 발급 {}", couponId4.longValue());
        log.info("쿠폰 ID in 사용자 쿠폰 발급 {}", couponId5.longValue());

        //when

        //couponIssuanceService.issueCoupon(user.getId(), couponId1); : 이미 보유중인 쿠폰입니다. 오류 메시지 확인 완료
        couponIssuanceService.issueCoupon(user.getId(), couponId2);
        couponIssuanceService.issueCoupon(user.getId(), couponId3);
        couponIssuanceService.issueCoupon(user.getId(), couponId4);
        couponIssuanceService.issueCoupon(user.getId(), couponId5);

        //then : CouponIssuance 만들어지는지 확인
        CouponIssuance couponIssuance1 = couponIssuanceRepository.findByUserIdAndCouponId(user.getId(), couponId1);
        log.info("발급 {} 발급 유저 {} 발급 쿠폰 핀 {}",couponIssuance1, couponIssuance1.getUser().getId().longValue(), couponIssuance1.getCoupon().getPin());

        CouponIssuance couponIssuance2 = couponIssuanceRepository.findByUserIdAndCouponId(user.getId(), couponId2);
        log.info("발급 {} 발급 유저 {} 발급 쿠폰 핀 {}",couponIssuance2, couponIssuance2.getUser().getId().longValue(), couponIssuance2.getCoupon().getPin());

        CouponIssuance couponIssuance3 = couponIssuanceRepository.findByUserIdAndCouponId(user.getId(), couponId3);
        log.info("발급 {} 발급 유저 {} 발급 쿠폰 핀 {}",couponIssuance3, couponIssuance3.getUser().getId().longValue(), couponIssuance3.getCoupon().getPin());

        CouponIssuance couponIssuance4 = couponIssuanceRepository.findByUserIdAndCouponId(user.getId(), couponId4);
        log.info("발급 {} 발급 유저 {} 발급 쿠폰 핀 {}",couponIssuance4, couponIssuance4.getUser().getId().longValue(), couponIssuance4.getCoupon().getPin());

        CouponIssuance couponIssuance5 = couponIssuanceRepository.findByUserIdAndCouponId(user.getId(), couponId5);
        log.info("발급 {} 발급 유저 {} 발급 쿠폰 핀 {}",couponIssuance5, couponIssuance5.getUser().getId().longValue(), couponIssuance5.getCoupon().getPin());

    }
    @Test
    @DisplayName("사용자가 발급한 쿠폰 중 미사용 쿠폰 테스트")//확인 완료
    void readUsableCouponsByUser(){
        issueCouponsByUser();
        couponIssuanceService.getUsableCouponList(user.getId());

    }
    @Test
    @DisplayName("사용자의 쿠폰 사용 테스트")//확인 완료
    void useCouponByUser(){
        issueCouponsByUser();

        CouponIssuanceDto.PinDto pinDto = new CouponIssuanceDto.PinDto(1111);

        couponIssuanceService.useCouponByUser(couponId1, user.getId(), pinDto);
    }
    @Test
    @DisplayName("사용자의 쿠폰 사용 후 미사용 쿠폰 및 사용완료한 쿠폰 조회 테스트")//확인 완료
    void useCouponsByUser(){
        issueCouponsByUser();
        couponIssuanceService.getUsableCouponList(user.getId());

        CouponIssuanceDto.PinDto pinDto1 = new CouponIssuanceDto.PinDto(1111);
        CouponIssuanceDto.PinDto pinDto2 = new CouponIssuanceDto.PinDto(2222);

        couponIssuanceService.useCouponByUser(couponId1, user.getId(), pinDto1);
        //couponIssuanceService.useCouponByUser(couponId1, user.getId(), pinDto2);//사용 불가능한 쿠폰입니다 확인 완료
        //couponIssuanceService.useCouponByUser(couponId2, user.getId(), pinDto1); //핀 번호 불일치 확인 완료
        couponIssuanceService.useCouponByUser(couponId2, user.getId(), pinDto2);

        couponIssuanceService.getUsableCouponList(user.getId());
        couponIssuanceService.getUsedCouponList(user.getId());
    }

}
