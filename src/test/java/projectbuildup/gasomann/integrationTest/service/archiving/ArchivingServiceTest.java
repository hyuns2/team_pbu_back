package projectbuildup.gasomann.integrationTest.service.archiving;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.gasomann.domain.archiving.repository.UserCardRepository;
import projectbuildup.gasomann.domain.archiving.service.ArchivingService;
import projectbuildup.gasomann.domain.coupon.entity.Coupon;
import projectbuildup.gasomann.domain.coupon.repository.CouponRepository;
import projectbuildup.gasomann.domain.user.entity.User;
import projectbuildup.gasomann.domain.user.repository.UserRepository;

@Slf4j
@SpringBootTest
@Transactional
public class ArchivingServiceTest {
    @Autowired
    private ArchivingService archivingService;
    @Autowired
    private UserCardRepository userCardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CouponRepository couponRepository;
    
    @Test
    @DisplayName("절약카드를 성공적으로 할당합니다. (더미데이터만 사용, so term 조건은 검증 불가능)")
    void assignSavingCards_success() {
        // given
        User user = userRepository.findById(1L).get();  // 4만원, 4번
        
        // when
        log.info("start");
        archivingService.assignSavingCards(user);
        log.info("end");

        // then
        Assertions.assertThat(userCardRepository.findAllByUserAndIsNew(user, true).size()).isEqualTo(4);
    }

    @Test
    @DisplayName("쿠폰카드를 성공적으로 할당합니다. 유저는 조건을 만족해 쿠폰을 할당받습니다. (더미데이터만 사용, so term 조건은 검증 불가능)")
    void assignCouponCards_success_satisfied() {
        // given
        User user = userRepository.findById(2L).get(); // id가 2인 쿠폰의 1번째 발급자
        Coupon coupon = couponRepository.findById(2L).get();

        // when
        log.info("start");
        archivingService.assignCouponCards(user, coupon);
        log.info("end");

        // then
        Assertions.assertThat(userCardRepository.findAllByUserAndIsNew(user, true).size()).isEqualTo(1);
    }

    @Test
    @DisplayName("쿠폰카드를 성공적으로 할당합니다. 유저는 조건을 만족하지 못해 쿠폰을 할당받지 않습니다. (더미데이터만 사용, so term 조건은 검증 불가능)")
    void assignCouponCards_success_unSatisfied() {
        // given
        User user = userRepository.findById(1L).get(); // id가 2인 쿠폰의 2번째 발급자
        Coupon coupon = couponRepository.findById(2L).get();

        // when
        log.info("start");
        archivingService.assignCouponCards(user, coupon);
        log.info("end");

        // then
        Assertions.assertThat(userCardRepository.findAllByUserAndIsNew(user, true).size()).isEqualTo(0);
    }
}
