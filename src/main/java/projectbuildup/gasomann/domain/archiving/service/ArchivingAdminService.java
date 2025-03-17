package projectbuildup.gasomann.domain.archiving.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.gasomann.domain.archiving.dto.ArchivingResponse;
import projectbuildup.gasomann.domain.archiving.dto.ArchivingRequest;
import projectbuildup.gasomann.domain.archiving.entity.*;
import projectbuildup.gasomann.domain.archiving.repository.CardRepository;
import projectbuildup.gasomann.domain.archiving.repository.UserCardRepository;
import projectbuildup.gasomann.domain.user.entity.User;
import projectbuildup.gasomann.domain.user.repository.UserRepository;
import projectbuildup.gasomann.global.common.fileStore.ExcelManager;
import projectbuildup.gasomann.global.common.imageStore.ImageType;
import projectbuildup.gasomann.global.common.imageStore.ImageUploader;
import projectbuildup.gasomann.global.error.exception.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArchivingAdminService {
    private final CardRepository<Card> cardRepository;
    private final UserCardRepository userCardRepository;
    private final UserRepository userRepository;
    private final ImageUploader imageUploader;
    private final ExcelManager excelManager;

    /**
     * 카드 생성
     */
    @Transactional
    public Long createCard(ArchivingRequest.CardCreateDto dto, MultipartFile image) {
        String imagePath = imageUploader.upload(image, ImageType.CARD).getImagePath();

        Card card = null;
        switch (dto.getCardType()) {
            case SAVING -> card = ArchivingRequest.SavingCardCreateDto.toEntity((ArchivingRequest.SavingCardCreateDto) dto, imagePath);
            case COUPON -> card = ArchivingRequest.CouponCardCreateDto.toEntity((ArchivingRequest.CouponCardCreateDto) dto, imagePath);
            case GENERAL -> card = ArchivingRequest.GeneralCardCreateDto.toEntity((ArchivingRequest.GeneralCardCreateDto) dto, imagePath);
        }

        return cardRepository.save(card).getId();
    }

    /**
     * 카드 수정
     */
    @Transactional
    public void updateCard(Long cardId, ArchivingRequest.CardUpdateDto dto, MultipartFile image) throws IOException {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(CCardNotFoundException::new);

        if (!dto.getCardType().equals(card.getCardType()))
            throw new CCardTypeNotMatchException();

        String imagePath = null;
        if (image != null) {
            imageUploader.deleteIfExists(card.getImagePath());
            imagePath = imageUploader.upload(image, ImageType.CARD).getImagePath();
        }

        switch (dto.getCardType()) {
            case SAVING -> ((SavingCard) card).update(dto.getTitle(), dto.getSubTitle(), dto.getSentences(), imagePath,
                    ((ArchivingRequest.SavingCardUpdateDto) dto).getCharge(), ((ArchivingRequest.SavingCardUpdateDto) dto).getCount(), ((ArchivingRequest.SavingCardUpdateDto) dto).getTerm());
            case COUPON -> ((CouponCard) card).update(dto.getTitle(), dto.getSubTitle(), dto.getSentences(), imagePath,
                    ((ArchivingRequest.CouponCardUpdateDto) dto).getCouponIssueOrder(), ((ArchivingRequest.CouponCardUpdateDto) dto).getContinuousIssueMonths());
            case GENERAL -> ((GeneralCard) card).update(dto.getTitle(), dto.getSubTitle(), dto.getSentences(), imagePath);
        }
    }

    /**
     * 카드 삭제
     */
    @Transactional
    public void deleteCard(Long cardId) {
        cardRepository.delete(cardRepository.findById(cardId)
                .orElseThrow(CCardNotFoundException::new));
    }

    /**
     * 카드 단건 조회
     */
    public ArchivingResponse.CardDto retrieveCard(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(CCardNotFoundException::new);

        ArchivingResponse.CardDto result = null;
        switch (card.getCardType()) {
            case SAVING -> result = ArchivingResponse.SavingCardDto.from((SavingCard) card);
            case COUPON -> result = ArchivingResponse.CouponCardDto.from((CouponCard) card);
            case GENERAL -> result = ArchivingResponse.GeneralCardDto.from((GeneralCard) card);
        }
        return result;
    }

    /**
     * 카드 전체 및 유형별 조회
     */
    public List<ArchivingResponse.CardDto> retrieveCards(CardType cardType) {
        List<Card> cards = cardRepository.findAllByTypeCond(cardType);

        return cards.stream().map(card ->
            switch (card.getCardType()) {
                case SAVING -> ArchivingResponse.SavingCardDto.from((SavingCard) card);
                case COUPON -> ArchivingResponse.CouponCardDto.from((CouponCard) card);
                case GENERAL -> ArchivingResponse.GeneralCardDto.from((GeneralCard) card);
            }).toList();
    }

    /**
     * 관리자가 엑셀의 유저들에게 일반 카드 할당하고, 존재하지 않는 유저들만 다시 엑셀로 반환
     */
    @Transactional
    public List<List<String>> assignGeneralCards(ArchivingRequest.GeneralCardAssignDto dto, HttpServletResponse response) throws IOException {
        Card card = cardRepository.findById(dto.getId())
                .orElseThrow(CCardNotFoundException::new);
        if (!card.getCardType().equals(CardType.GENERAL))
            throw new CCardTypeNotMatchException();

        Sheet sheet = excelManager.storeExcelAndRetrieveFirstSheet(dto.getFile());
        List<List<String>> targetUsers = parseExcel(sheet);
        List<List<String>> notFoundUsers = assignCards(card, targetUsers);

        Workbook workbook = excelManager.writeExcel(notFoundUsers);
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();

        return notFoundUsers;
    }

    private List<List<String>> parseExcel(Sheet sheet) {
        List<List<String>> targetUsers = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null)
                throw new CInvalidCellException((rowIndex+1) + "행의 문제");

            Cell cell0 = row.getCell(0);
            Cell cell1 = row.getCell(1);
            if (cell0 == null || cell0.getCellType() != CellType.STRING || cell1 == null || cell1.getCellType() != CellType.STRING)
                throw new CInvalidCellException((rowIndex+1) + "행의 문제");

            targetUsers.add(List.of(cell0.getStringCellValue(), cell1.getStringCellValue()));
        }

        return targetUsers;
    }

    private List<List<String>> assignCards(Card card, List<List<String>> targetUsers) {
        List<List<String>> notFoundUsers = new ArrayList<>();
        List<User> foundUsers = new ArrayList<>();

        targetUsers.forEach(targetUser -> {
            Optional<User> user = userRepository.findByNameAndMobile(targetUser.get(0), targetUser.get(1));
            if (user.isEmpty())
                notFoundUsers.add(targetUser);
            else
                foundUsers.add(user.get());
        });

        List<Long> existingCardUserIds = userCardRepository.findUserIdsByCardAndUserIn(card, foundUsers);
        List<UserCard> userCards = foundUsers.stream()
                .filter(user -> !existingCardUserIds.contains(user.getId()))
                .map(user -> UserCard.of(user, card)).toList();

        userCardRepository.saveAll(userCards);
        return notFoundUsers;
    }
}
