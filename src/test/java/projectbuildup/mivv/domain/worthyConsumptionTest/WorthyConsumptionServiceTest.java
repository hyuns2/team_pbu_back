package projectbuildup.mivv.domain.worthyConsumptionTest;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import projectbuildup.mivv.common.MockEntity;
import projectbuildup.mivv.domain.coupon.dto.request.CouponRequestDto;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.coupon.repository.CouponRepository;
import projectbuildup.mivv.domain.coupon.service.CouponService;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.domain.worthyConsumption.repository.WorthyConsumptionRepository;
import projectbuildup.mivv.domain.worthyConsumption.service.WorthyConsumptionService;
import projectbuildup.mivv.global.error.exception.CWorthyConsumptionNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Disabled
@SpringBootTest
@Slf4j
public class WorthyConsumptionServiceTest  extends MockEntity {
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    CouponService couponService;
    @Autowired
    WorthyConsumptionRepository worthyConsumptionRepository;
    @Autowired
    WorthyConsumptionService worthyConsumptionService;
    public Long couponId1;
    public Long couponId2;
    public Long couponId3;
    public Long couponId4;
    public Long couponId5;

    @Test
    @DisplayName("가치소비를 만드는 테스트")//확인 완료
    void testCreationWorthyConsumption(){
        /*
        given, when
        가치소비 생성
         */

        WorthyConsumption worthyConsumption = MOCK_WORTHY_CONSUMPTION;
        WorthyConsumption worthyConsumption_saved = worthyConsumptionRepository.save(worthyConsumption);

        log.info("생성된 가치소비는 {}", worthyConsumption);
        log.info("저장된 가치소비는 {}", worthyConsumption_saved);


        //then
        WorthyConsumption result = worthyConsumptionRepository.findById(worthyConsumption.getId()).orElseThrow(CWorthyConsumptionNotFoundException::new);
        log.info("저장된 가치소비는 {}", result);

    }
    @Test
    @DisplayName("가치소비에서 쿠폰을 생성하는 테스트")
    public//확인 완료
    void testCreationCouponAtWorthyConsumption(){
        /*
        given
        가치소비 생성
         */

        WorthyConsumption worthyConsumption = MOCK_WORTHY_CONSUMPTION;
        WorthyConsumption worthyConsumption_saved = worthyConsumptionRepository.save(worthyConsumption);

        log.info("생성된 가치소비는 {}", worthyConsumption);
        log.info("저장된 가치소비는 {}", worthyConsumption_saved);

        /*
        when
         */
        CouponRequestDto.CreationRequest couponDto1 = new CouponRequestDto.CreationRequest(
                "쿠폰 제목",
                "쿠폰 이미지 url",
                1111,
                LocalDate.of(2023, 4, 18),
                LocalDate.of(2023, 4, 19)
        );
        CouponRequestDto.CreationRequest couponDto2 = new CouponRequestDto.CreationRequest(
                "쿠폰 제목",
                "쿠폰 이미지 url",
                2222,
                LocalDate.of(2023, 4, 18),
                LocalDate.of(2023, 4, 19)
        );
        CouponRequestDto.CreationRequest couponDto3 = new CouponRequestDto.CreationRequest(
                "쿠폰 제목",
                "쿠폰 이미지 url",
                3333,
                LocalDate.of(2023, 4, 18),
                LocalDate.of(2023, 4, 19)
        );
        CouponRequestDto.CreationRequest couponDto4 = new CouponRequestDto.CreationRequest(
                "쿠폰 제목",
                "쿠폰 이미지 url",
                4444,
                LocalDate.of(2023, 4, 18),
                LocalDate.of(2023, 4, 19)
        );
        CouponRequestDto.CreationRequest couponDto5 = new CouponRequestDto.CreationRequest(
                "쿠폰 제목",
                "쿠폰 이미지 url",
                5555,
                LocalDate.of(2023, 4, 18),
                LocalDate.of(2023, 4, 19)
        );
        couponService.createCoupon(worthyConsumption.getId(), couponDto1);
        couponService.createCoupon(worthyConsumption.getId(), couponDto2);
        couponService.createCoupon(worthyConsumption.getId(), couponDto3);
        couponService.createCoupon(worthyConsumption.getId(), couponDto4);
        couponService.createCoupon(worthyConsumption.getId(), couponDto5);

        //then
        WorthyConsumption result = worthyConsumptionRepository.findById(worthyConsumption.getId()).orElseThrow(CWorthyConsumptionNotFoundException::new);
        log.info("저장된 가치소비는 {}", result);
        log.info("쿠폰 개수 {}", result.getCoupons().stream().collect(Collectors.toList()).stream().count());//확인 완료

        List<Coupon> couponList = result.getCoupons().stream().toList();
        log.info("쿠폰1 {}", couponList.get(0).getPin());
        log.info("쿠폰2 {}", couponList.get(1).getPin());
        log.info("쿠폰3 {}", couponList.get(2).getPin());
        log.info("쿠폰4 {}", couponList.get(3).getPin());
        log.info("쿠폰5 {}", couponList.get(4).getPin());

        couponId1 = couponList.get(0).getId();
        couponId2 = couponList.get(1).getId();
        couponId3 = couponList.get(2).getId();
        couponId4 = couponList.get(3).getId();
        couponId5 = couponList.get(4).getId();

        log.info("쿠폰 ID in 쿠폰 발급 {}", couponId1.longValue());
        log.info("쿠폰 ID in 쿠폰 발급 {}", couponId2.longValue());
        log.info("쿠폰 ID in 쿠폰 발급 {}", couponId3.longValue());
        log.info("쿠폰 ID in 쿠폰 발급 {}", couponId4.longValue());
        log.info("쿠폰 ID in 쿠폰 발급 {}", couponId5.longValue());

        Assertions.assertThat(result.getCoupons().stream().collect(Collectors.toList())).isNotEmpty();

    }
}
