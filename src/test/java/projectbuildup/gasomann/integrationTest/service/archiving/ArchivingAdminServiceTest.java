package projectbuildup.gasomann.integrationTest.service.archiving;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.gasomann.domain.archiving.dto.ArchivingRequest;
import projectbuildup.gasomann.domain.archiving.dto.ArchivingResponse;
import projectbuildup.gasomann.domain.archiving.entity.*;
import projectbuildup.gasomann.domain.archiving.repository.CardRepository;
import projectbuildup.gasomann.domain.archiving.repository.UserCardRepository;
import projectbuildup.gasomann.domain.archiving.service.ArchivingAdminService;
import projectbuildup.gasomann.domain.user.entity.User;
import projectbuildup.gasomann.domain.user.repository.UserRepository;
import projectbuildup.gasomann.global.common.fileStore.ExcelManager;
import projectbuildup.gasomann.global.error.exception.CCardTypeNotMatchException;
import projectbuildup.gasomann.global.error.exception.CInvalidCellException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
@Transactional
public class ArchivingAdminServiceTest {
    @Autowired
    ArchivingAdminService archivingAdminService;
    @Autowired
    CardRepository<Card> cardRepository;
    @Autowired
    UserCardRepository userCardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ExcelManager excelManager;
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("절약카드를 성공적으로 생성합니다.")
    void createCard_success_savingCard() {
        // given
        ArchivingRequest.SavingCardCreateDto savingCardDto = ArchivingRequest.SavingCardCreateDto.of(
                "제목", "부제목", List.of("명언1", "명언2"), 10000, 2, 0);
        MockMultipartFile file = new MockMultipartFile("test file", "test.png", MediaType.IMAGE_PNG_VALUE, "test file".getBytes());

        // when
        log.info("start");
        Long savingCardId = archivingAdminService.createCard(savingCardDto, file);
        log.info("end");

        // then
        SavingCard savingCard = (SavingCard) cardRepository.findById(savingCardId).get();
        Assertions.assertThat(savingCard.getCardType()).isEqualTo(CardType.SAVING);
        Assertions.assertThat(savingCard.getCharge()).isEqualTo(10000);
        Assertions.assertThat(savingCard.getCount()).isEqualTo(2);
        Assertions.assertThat(savingCard.getTerm()).isEqualTo(0);
    }

    @Test
    @DisplayName("쿠폰카드를 성공적으로 생성합니다.")
    void createCard_success_couponCard() {
        // given
        ArchivingRequest.CouponCardCreateDto couponCardDto = ArchivingRequest.CouponCardCreateDto.of(
                "제목", "부제목", List.of("명언1", "명언2"), 1, 1);
        MockMultipartFile file = new MockMultipartFile("test file", "test.png", MediaType.IMAGE_PNG_VALUE, "test file".getBytes());

        // when
        log.info("start");
        Long couponCardId = archivingAdminService.createCard(couponCardDto, file);
        log.info("end");

        // then
        CouponCard couponCard = (CouponCard) cardRepository.findById(couponCardId).get();
        Assertions.assertThat(couponCard.getCardType()).isEqualTo(CardType.COUPON);
        Assertions.assertThat(couponCard.getCouponIssueOrder()).isEqualTo(1);
        Assertions.assertThat(couponCard.getContinuousIssueMonths()).isEqualTo(1);
    }

    @Test
    @DisplayName("일반카드를 성공적으로 생성합니다.")
    void createCard_success_generalCard() {
        // given
        ArchivingRequest.GeneralCardCreateDto generalCardDto = ArchivingRequest.GeneralCardCreateDto.of(
                "제목", "부제목", List.of("명언1", "명언2"));
        MockMultipartFile file = new MockMultipartFile("test file", "test.png", MediaType.IMAGE_PNG_VALUE, "test file".getBytes());

        // when
        log.info("start");
        Long generalCardId = archivingAdminService.createCard(generalCardDto, file);
        log.info("end");

        // then
        GeneralCard generalCard = (GeneralCard) cardRepository.findById(generalCardId).get();
        Assertions.assertThat(generalCard.getCardType()).isEqualTo(CardType.GENERAL);
    }

    @Test
    @DisplayName("절약카드를 성공적으로 수정합니다.")
    void updateCard_success_savingCard() throws IOException {
        // given
        Long cardId = cardRepository.save(SavingCard.of(
                "제목", "부제목", List.of("명언1", "명언2"), "before image path", 10000, 2, 0)).getId();

        ArchivingRequest.SavingCardUpdateDto dto = ArchivingRequest.SavingCardUpdateDto.of(
                null, null, null, 20000, null, null);
        MockMultipartFile file = new MockMultipartFile("test file", "test.png", MediaType.IMAGE_PNG_VALUE, "test file".getBytes());

        // when
        log.info("start");
        archivingAdminService.updateCard(cardId, dto, file);

        entityManager.flush();
        entityManager.clear();
        log.info("end");

        // then
        SavingCard updatedCard = (SavingCard) cardRepository.findById(cardId).get();
        Assertions.assertThat(updatedCard.getImagePath()).isNotEqualTo("before image path");
        Assertions.assertThat(updatedCard.getCharge()).isEqualTo(20000);

    }

    @Test
    @DisplayName("쿠폰카드를 성공적으로 수정합니다.")
    void updateCard_success_couponCard() throws IOException {
        // given
        Long cardId = cardRepository.save(CouponCard.of(
                "제목", "부제목", List.of("명언1", "명언2"), "before image path", 1, 1)).getId();

        ArchivingRequest.CouponCardUpdateDto dto = ArchivingRequest.CouponCardUpdateDto.of(
                null, null, null, null, 2);
        MockMultipartFile file = new MockMultipartFile("test file", "test.png", MediaType.IMAGE_PNG_VALUE, "test file".getBytes());

        // when
        log.info("start");
        archivingAdminService.updateCard(cardId, dto, file);

        entityManager.flush();
        entityManager.clear();
        log.info("end");

        // then
        CouponCard updatedCard = (CouponCard) cardRepository.findById(cardId).get();
        Assertions.assertThat(updatedCard.getImagePath()).isNotEqualTo("before image path");
        Assertions.assertThat(updatedCard.getContinuousIssueMonths()).isEqualTo(2);
    }

    @Test
    @DisplayName("일반카드를 성공적으로 수정합니다.")
    void updateCard_success_generalCard() throws IOException {
        // given
        Long cardId = cardRepository.save(GeneralCard.of(
                "제목", "부제목", List.of("명언1", "명언2"), "before image path")).getId();
        ArchivingRequest.GeneralCardUpdateDto dto = ArchivingRequest.GeneralCardUpdateDto.of(
                null, null, null);
        MockMultipartFile file = new MockMultipartFile("test file", "test.png", MediaType.IMAGE_PNG_VALUE, "test file".getBytes());

        // when
        log.info("start");
        archivingAdminService.updateCard(cardId, dto, file);

        entityManager.flush();
        entityManager.clear();
        log.info("end");

        // then
        GeneralCard updatedCard = (GeneralCard) cardRepository.findById(cardId).get();
        Assertions.assertThat(updatedCard.getImagePath()).isNotEqualTo("before image path");
    }

    @Test
    @DisplayName("수정하려는 카드와 수정 요청의 카드 타입이 일치하지 않아, 카드 수정을 실패합니다.")
    void updateCard_fail_1() {
        // given
        Long cardId = cardRepository.save(GeneralCard.of(
                "제목", "부제목", List.of("명언1", "명언2"), "before image path")).getId();

        ArchivingRequest.SavingCardUpdateDto dto = ArchivingRequest.SavingCardUpdateDto.of(
                null, null, null, 20000, null, null);
        MockMultipartFile file = new MockMultipartFile("test file", "test.png", MediaType.IMAGE_PNG_VALUE, "test file".getBytes());

        // when, then
        log.info("start");
        Assertions.assertThatThrownBy(() -> archivingAdminService.updateCard(cardId, dto, file)).isInstanceOf(CCardTypeNotMatchException.class);
        log.info("end");
    }

    @Test
    @DisplayName("카드를 성공적으로 삭제합니다.")
    void deleteCard_success() {
        // given
        Long cardId = cardRepository.save(GeneralCard.of(
                "제목", "부제목", List.of("명언1", "명언2"), "test path")).getId();

        // when
        log.info("start");
        archivingAdminService.deleteCard(cardId);

        entityManager.flush();
        entityManager.clear();
        log.info("end");

        // then
        Assertions.assertThat(cardRepository.findById(cardId).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("절약카드 하나를 성공적으로 조회합니다.")
    void retrieveCard_success_savingCard() {
        // given
        Long cardId = cardRepository.save(SavingCard.of(
                "제목", "부제목", List.of("명언1", "명언2"), "test path", 10000, 2, 0)).getId();

        // when
        log.info("start");
        ArchivingResponse.CardDto response = archivingAdminService.retrieveCard(cardId);
        log.info("end");

        // then
        Assertions.assertThat(response.getCardType()).isEqualTo(CardType.SAVING);
        Assertions.assertThat(((ArchivingResponse.SavingCardDto) response).getCharge()).isEqualTo(10000);
        Assertions.assertThat(((ArchivingResponse.SavingCardDto) response).getCount()).isEqualTo(2);
        Assertions.assertThat(((ArchivingResponse.SavingCardDto) response).getTerm()).isEqualTo(0);
    }

    @Test
    @DisplayName("쿠폰카드 하나를 성공적으로 조회합니다.")
    void retrieveCard_success_couponCard() {
        // given
        Long cardId = cardRepository.save(CouponCard.of(
                "제목", "부제목", List.of("명언1", "명언2"), "test path", 1, 1)).getId();

        // when
        log.info("start");
        ArchivingResponse.CardDto response = archivingAdminService.retrieveCard(cardId);
        log.info("end");

        // then
        Assertions.assertThat(response.getCardType()).isEqualTo(CardType.COUPON);
        Assertions.assertThat(((ArchivingResponse.CouponCardDto) response).getCouponIssueOrder()).isEqualTo(1);
        Assertions.assertThat(((ArchivingResponse.CouponCardDto) response).getContinuousIssueMonths()).isEqualTo(1);
    }

    @Test
    @DisplayName("절약카드 하나를 성공적으로 조회합니다.")
    void retrieveCard_success_generalCard() {
        // given
        Long cardId = cardRepository.save(GeneralCard.of(
                "제목", "부제목", List.of("명언1", "명언2"), "test path")).getId();

        // when
        log.info("start");
        ArchivingResponse.CardDto response = archivingAdminService.retrieveCard(cardId);
        log.info("end");

        // then
        Assertions.assertThat(response.getCardType()).isEqualTo(CardType.GENERAL);
    }

    @Test
    @DisplayName("전체 카드를 성공적으로 조회합니다. (더미데이터 포함)")
    void retrieveCards_success_all() {
        // given
        cardRepository.save(SavingCard.of(
                "제목", "부제목", List.of("명언1", "명언2"), "test path", 10000, 2, 0));
        cardRepository.save(CouponCard.of(
                "제목", "부제목", List.of("명언1", "명언2"), "test path", 1, 1));
        cardRepository.save(GeneralCard.of(
                "제목", "부제목", List.of("명언1", "명언2"), "test path"));

        // when
        log.info("start");
        List<ArchivingResponse.CardDto> response = archivingAdminService.retrieveCards(null);
        log.info("end");

        // then
        Assertions.assertThat(response.size()).isEqualTo(3 + 13);
    }

    @Test
    @DisplayName("절약카드 전체를 성공적으로 조회합니다. (더미데이터 포함)")
    void retrieveCards_success_savingCards() {
        // given
        cardRepository.save(SavingCard.of(
                "제목", "부제목", List.of("명언1", "명언2"), "test path", 10000, 2, 0));

        // when
        log.info("start");
        List<ArchivingResponse.CardDto> response = archivingAdminService.retrieveCards(CardType.SAVING);
        log.info("end");

        // then
        Assertions.assertThat(response.size()).isEqualTo(1 + 10);
    }

    @Test
    @DisplayName("쿠폰카드 전체를 성공적으로 조회합니다. (더미데이터 포함)")
    void retrieveCards_success_couponCards() {
        // given
        cardRepository.save(CouponCard.of(
                "제목", "부제목", List.of("명언1", "명언2"), "test path", 1, 1));

        // when
        log.info("start");
        List<ArchivingResponse.CardDto> response = archivingAdminService.retrieveCards(CardType.COUPON);
        log.info("end");

        // then
        Assertions.assertThat(response.size()).isEqualTo(1 + 2);
    }

    @Test
    @DisplayName("일반카드 전체를 성공적으로 조회합니다. (더미데이터 포함)")
    void retrieveCards_success_generalCards() {
        // given
        cardRepository.save(GeneralCard.of(
                "제목", "부제목", List.of("명언1", "명언2"), "test path"));

        // when
        log.info("start");
        List<ArchivingResponse.CardDto> response = archivingAdminService.retrieveCards(CardType.GENERAL);
        log.info("end");

        // then
        Assertions.assertThat(response.size()).isEqualTo(1 + 1);
    }

    @Test
    @DisplayName("일반카드를 성공적으로 할당합니다. (카드, 유저 더미데이터 사용)")
    void assignGeneralCards_success() throws IOException {
        // given
        List<List<String>> excelData = new ArrayList<>();
        excelData.add(List.of("aaa", "01012345671"));
        excelData.add(List.of("notFoundUser", "01000000000"));
        Workbook workbook = excelManager.writeExcel(excelData);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        workbook.close();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("test file", "test.xlsx", MediaType.MULTIPART_FORM_DATA_VALUE, byteArrayOutputStream.toByteArray());  // dto 생성 시작
        ArchivingRequest.GeneralCardAssignDto dto = ArchivingRequest.GeneralCardAssignDto.of(1L, mockMultipartFile);

        // when
        log.info("start");
        List<List<String>> notFoundUsers = archivingAdminService.assignGeneralCards(dto, new MockHttpServletResponse());
        log.info("end");

        // then
        User user = userRepository.findByNameAndMobile("aaa", "01012345671").get();
        Assertions.assertThat(userCardRepository.findByUserIdAndCardId(user.getId(), 1L)).isNotEmpty();
        Assertions.assertThat(notFoundUsers.size()).isEqualTo(1);
        Assertions.assertThat(notFoundUsers.get(0).get(0)).isEqualTo("notFoundUser");
        Assertions.assertThat(notFoundUsers.get(0).get(1)).isEqualTo("01000000000");
    }

    @Test
    @DisplayName("카드 타입이 일반이 아닌 카드로 요청해, 일반카드 할당에 실패합니다. (카드 더미데이터 사용)")
    void assignGeneralCards_fail_1() {
        // given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test file", "test.xlsx", MediaType.MULTIPART_FORM_DATA_VALUE, "test file".getBytes());
        ArchivingRequest.GeneralCardAssignDto dto = ArchivingRequest.GeneralCardAssignDto.of(2L, mockMultipartFile); // 절약카드로 요청

        // when, then
        log.info("start");
        Assertions.assertThatThrownBy(() -> archivingAdminService.assignGeneralCards(dto, new MockHttpServletResponse())).isInstanceOf(CCardTypeNotMatchException.class);
        log.info("end");
    }

    @Test
    @DisplayName("엑셀파일의 서식이 맞지 않아, 일반카드 할당에 실패합니다. (카드, 유저 더미데이터 사용)")
    void assignGeneralCards_fail_2() throws IOException {
        // given
        List<List<String>> excelData = new ArrayList<>();
        excelData.add(List.of());
        excelData.add(List.of("aaa", "01012345671"));
        Workbook workbook = excelManager.writeExcel(excelData);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        workbook.close();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("test file", "test.xlsx", MediaType.MULTIPART_FORM_DATA_VALUE, byteArrayOutputStream.toByteArray());  // dto 생성 시작
        ArchivingRequest.GeneralCardAssignDto dto = ArchivingRequest.GeneralCardAssignDto.of(1L, mockMultipartFile);

        // when, then
        log.info("start");
        Assertions.assertThatThrownBy(() -> archivingAdminService.assignGeneralCards(dto, new MockHttpServletResponse())).isInstanceOf(CInvalidCellException.class);
        log.info("end");
    }
}
