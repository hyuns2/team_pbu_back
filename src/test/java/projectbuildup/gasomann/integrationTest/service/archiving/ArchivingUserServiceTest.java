package projectbuildup.gasomann.integrationTest.service.archiving;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.gasomann.domain.archiving.dto.ArchivingResponse;
import projectbuildup.gasomann.domain.archiving.entity.*;
import projectbuildup.gasomann.domain.archiving.repository.CardRepository;
import projectbuildup.gasomann.domain.archiving.repository.UserCardRepository;
import projectbuildup.gasomann.domain.archiving.service.ArchivingUserService;
import projectbuildup.gasomann.domain.user.entity.User;
import projectbuildup.gasomann.domain.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;

@Slf4j
@SpringBootTest
@Transactional
public class ArchivingUserServiceTest {
    @Autowired
    ArchivingUserService archivingUserService;
    @Autowired
    CardRepository<Card> cardRepository;
    @Autowired
    UserCardRepository userCardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager entityManager;

    User user;
    GeneralCard generalCard;
    SavingCard savingCard;
    CouponCard couponCard;

    @BeforeEach
    void beforeEach() {
        user = userRepository.findById(1L).get();
        generalCard = (GeneralCard) cardRepository.findById(1L).get();
        savingCard = (SavingCard) cardRepository.findById(2L).get();
        couponCard = (CouponCard) cardRepository.findById(12L).get();

        userCardRepository.saveAll(List.of(
                UserCard.of(user, generalCard), UserCard.of(user, savingCard), UserCard.of(user, couponCard)));
    }

    @Test
    @DisplayName("사용자의 새 카드 전체를 반환 및 신규 여부를 갱신에 성공합니다. (카드, 유저 더미데이터 사용)")
    void retrieveAndUpdateNewCards_success() {
        // given

        // when
        log.info("start");
        List<ArchivingResponse.UserCardWithCardSummaryDto> dtos = archivingUserService.retrieveAndUpdateNewCards(user);
        log.info("end");

        // then
        Assertions.assertThat(dtos.size()).isEqualTo(3);
        Assertions.assertThat(dtos.stream()
                .map(ArchivingResponse.UserCardWithCardSummaryDto::getIsNew).toList()).containsOnly(true);
        Assertions.assertThat(userCardRepository.findAllById(dtos.stream()
                .map(ArchivingResponse.UserCardWithCardSummaryDto::getId).toList()).stream()
                .map(UserCard::getIsNew).toList()).containsOnly(false);
    }

    @Test
    @DisplayName("사용자 카드 정보를 포함한 전체 카드 요약 정보를 성공적으로 조회합니다. (카드, 유저 더미데이터 사용)")
    void retrieveUserCards_success_all() {
        // given

        // when
        log.info("start");
        List<ArchivingResponse.CardSummaryAndUserCardDto> dtos = archivingUserService.retrieveUserCards(user, null);
        log.info("end");

        // then
        Assertions.assertThat(dtos.size()).isEqualTo(13);
        Assertions.assertThat(dtos.stream()
                .map(ArchivingResponse.CardSummaryAndUserCardDto::getUserCardDto)
                .filter(Objects::nonNull).toList().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("사용자 카드 정보를 포함한 전체 절약카드 요약 정보를 성공적으로 조회합니다. (카드, 유저 더미데이터 사용)")
    void retrieveUserCards_success_savingCards() {
        // given

        // when
        log.info("start");
        List<ArchivingResponse.CardSummaryAndUserCardDto> dtos = archivingUserService.retrieveUserCards(user, CardType.SAVING);
        log.info("end");

        // then
        Assertions.assertThat(dtos.size()).isEqualTo(10);
        Assertions.assertThat(dtos.stream()
                .map(ArchivingResponse.CardSummaryAndUserCardDto::getUserCardDto)
                .filter(Objects::nonNull).toList().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("사용자 카드 정보를 포함한 전체 쿠폰카드 요약 정보를 성공적으로 조회합니다. (카드, 유저 더미데이터 사용)")
    void retrieveUserCards_success_couponCards() {
        // given

        // when
        log.info("start");
        List<ArchivingResponse.CardSummaryAndUserCardDto> dtos = archivingUserService.retrieveUserCards(user, CardType.COUPON);
        log.info("end");

        // then
        Assertions.assertThat(dtos.size()).isEqualTo(2);
        Assertions.assertThat(dtos.stream()
                .map(ArchivingResponse.CardSummaryAndUserCardDto::getUserCardDto)
                .filter(Objects::nonNull).toList().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("사용자 카드 정보를 포함한 전체 일반카드 요약 정보를 성공적으로 조회합니다. (카드, 유저 더미데이터 사용)")
    void retrieveUserCards_success_generalCards() {
        // given

        // when
        log.info("start");
        List<ArchivingResponse.CardSummaryAndUserCardDto> dtos = archivingUserService.retrieveUserCards(user, CardType.GENERAL);
        log.info("end");

        // then
        Assertions.assertThat(dtos.size()).isEqualTo(1);
        Assertions.assertThat(dtos.stream()
                .map(ArchivingResponse.CardSummaryAndUserCardDto::getUserCardDto)
                .filter(Objects::nonNull).toList().size()).isEqualTo(1);
    }
}
