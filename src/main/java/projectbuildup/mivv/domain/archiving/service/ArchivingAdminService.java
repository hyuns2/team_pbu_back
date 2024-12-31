package projectbuildup.mivv.domain.archiving.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.mivv.domain.archiving.dto.ArchivingResponse;
import projectbuildup.mivv.domain.archiving.dto.ArchivingRequest;
import projectbuildup.mivv.domain.archiving.entity.*;
import projectbuildup.mivv.domain.archiving.repository.CardRepository;
import projectbuildup.mivv.domain.archiving.repository.UserCardRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.common.fileStore.ExcelManager;
import projectbuildup.mivv.global.common.imageStore.Image;
import projectbuildup.mivv.global.common.imageStore.ImageType;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
import projectbuildup.mivv.global.error.exception.CCardNotFoundException;
import projectbuildup.mivv.global.error.exception.CCardTypeNotMatchException;
import projectbuildup.mivv.global.error.exception.CInvalidCardConditionException;
import projectbuildup.mivv.global.error.exception.CInvalidCellException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ArchivingAdminService {
    private final CardRepository<Card> cardRepository;
    private final UserCardRepository userCardRepository;
    private final UserRepository userRepository;
    private final ImageUploader imageUploader;
    private final ExcelManager excelManager;

    /**
     * 절약 카드 생성 및 수정
     *
     * @param cardId 수정할 id
     * @param dto 카드 정보, 발급 조건
     * @throws CInvalidCardConditionException 카드 조건이 없을 시
     * @throws CCardNotFoundException 수정할 카드가 존재하지 않을 시
     */
    @Transactional
    public void putSavingCard(Long cardId, ArchivingRequest.SavingCardDto dto) throws IOException {
        if (dto.getCharge() == 0 && dto.getCount() == 0 && dto.getTerm() == 0) {
            throw new CInvalidCardConditionException();
        }

        Image image = imageUploader.upload(dto.getCardDto().getImage(), ImageType.CARD);
        if (cardId != null) {
            Card card = cardRepository.findById(cardId)
                    .orElseThrow(CCardNotFoundException::new);
            SavingCard savingCard = (SavingCard) card;
            savingCard.update(dto.getCardDto().getTitle(),
                    dto.getCardDto().getSubTitle(),
                    dto.getCardDto().getSentences(),
                    image.getImagePath(),
                    dto.getCharge(),
                    dto.getCount(),
                    dto.getTerm());
        }
        else {
            SavingCard entity = ArchivingRequest.SavingCardDto.toEntity(dto, image.getImagePath());
            cardRepository.save(entity);
        }
    }

    /**
     * 소비카드 생성 및 수정
     *
     * @param cardId 수정할 id
     * @param dto 소비 정보, 발급 조건
     * @throws CInvalidCardConditionException 카드 조건이 없을 시
     * @throws CCardNotFoundException 수정할 카드가 존재하지 않을 시
     */
    @Transactional
    public void putCouponCard(Long cardId, ArchivingRequest.CouponCardDto dto) throws IOException {
        if (dto.getWhatNumber() == 0 && dto.getHowSuccessive() == 0) {
            throw new CInvalidCardConditionException();
        }

        Image image = imageUploader.upload(dto.getCardDto().getImage(), ImageType.CARD);
        if (cardId != null) {
            Card card = cardRepository.findById(cardId)
                    .orElseThrow(CCardNotFoundException::new);
            CouponCard couponCard = (CouponCard) card;
            couponCard.update(dto.getCardDto().getTitle(),
                    dto.getCardDto().getSubTitle(),
                    dto.getCardDto().getSentences(),
                    image.getImagePath(),
                    dto.getWhatNumber(),
                    dto.getHowSuccessive());
        }
        else {
            CouponCard entity = ArchivingRequest.CouponCardDto.toEntity(dto, image.getImagePath());
            cardRepository.save(entity);
        }
    }

    /**
     * 일반카드 생성 및 수정
     *
     * @param cardId 수정할 id
     * @param dto 소비 정보, 발급 조건
     * @throws CCardNotFoundException 수정할 카드가 존재하지 않을 시
     */
    @Transactional
    public void putGeneralCard(Long cardId, ArchivingRequest.GeneralCardDto dto) throws IOException {
        Image image = imageUploader.upload(dto.getCardDto().getImage(), ImageType.CARD);
        if (cardId != null) {
            Card card = cardRepository.findById(cardId)
                    .orElseThrow(CCardNotFoundException::new);
            GeneralCard GeneralCard = (GeneralCard) card;
            GeneralCard.update(dto.getCardDto().getTitle(),
                    dto.getCardDto().getSubTitle(),
                    dto.getCardDto().getSentences(),
                    image.getImagePath());
        }
        else {
            GeneralCard entity = ArchivingRequest.GeneralCardDto.toEntity(dto, image.getImagePath());
            cardRepository.save(entity);
        }
    }

    /**
     * 카드 삭제
     *
     * @param cardId 삭제할 Id
     * @throws CCardNotFoundException 카드 찾기 실패 시
     */
    @Transactional
    public void deleteCard(Long cardId) {
        cardRepository.delete(cardRepository.findById(cardId)
                .orElseThrow(CCardNotFoundException::new));
    }

    /**
     * 카드 단건 조회
     *
     * @param id 카드 Id
     * @return ArchivingDto.CardResponseDto 카드 정보 전체
     * @throws CCardNotFoundException 카드 찾기 실패시
     */
    @Transactional(readOnly = true)
    public ArchivingResponse.CardDto retrieveCard(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(CCardNotFoundException::new);

        if (card.getType().equals(CardType.SAVING))
            return ArchivingResponse.SavingCardDto.of((SavingCard) card);
        else if (card.getType().equals(CardType.COUPON))
            return ArchivingResponse.CouponCardDto.of((CouponCard) card);
        else if (card.getType().equals(CardType.GENERAL))
            return ArchivingResponse.GeneralCardDto.of((GeneralCard) card);
        else
            throw new CCardTypeNotMatchException();
    }

    /**
     * 카드 전체 및 유형별 조회
     *
     * @return List<ArchivingDto.CardResponse> 카드들의 정보 전체
     */
    @Transactional(readOnly = true)
    public List<ArchivingResponse.CardDto> retrieveCards(CardType cardType) {
        List<Card> cardList = cardRepository.findCardByTypeCond(cardType);

        return cardList.stream().map(ArchivingResponse.CardDto::of).toList();
    }

    /**
     * 관리자가 엑셀의 유저들에게 일반 카드 할당하고, 존재하지 않는 유저들만 다시 엑셀로 반환
     *
     * @param dto 카드 Id, 엑셀파일
     * @throws CCardNotFoundException 카드 찾기 실패 시
     * @throws CCardTypeNotMatchException 일반카드가 아닐 시
     * @throws CInvalidCellException 엑셀의 셀이 유효하지 않을 시
     */
    @Transactional
    public void assignGeneralCards(ArchivingRequest.AssignGeneralCardDto dto, final HttpServletResponse response) throws IOException {
        Card card = cardRepository.findById(dto.getId())
                .orElseThrow(CCardNotFoundException::new);
        if (card.getType().equals(CardType.GENERAL))
            throw new CCardTypeNotMatchException();

        Sheet sheet = excelManager.storeExcelAndRetrieveFirstSheet(dto.getFile());
        Map<String, String> notFoundUserMap = parseAndAssign(sheet, card);
        excelManager.writeExcel(response, notFoundUserMap, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    private Map<String, String> parseAndAssign(Sheet sheet, Card card) {
        Map<String, String> notFoundUserMap = new HashMap<>();

        for (int rowIndex = 0; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null)
                throw new CInvalidCellException((rowIndex+1) + "행의 문제");

            Cell cell0 = row.getCell(0);
            Cell cell1 = row.getCell(1);
            if (cell0 == null || cell0.getCellType() != CellType.STRING || cell1 == null || cell1.getCellType() != CellType.STRING)
                throw new CInvalidCellException((rowIndex+1) + "행의 문제");

            String name = cell0.getStringCellValue();
            String mobile = cell1.getStringCellValue();

            if (!assignCard(card, name, mobile))
                notFoundUserMap.put(name, mobile);
        }

        return notFoundUserMap;
    }

    private boolean assignCard(Card card, String name, String mobile) {
        Optional<User> user = userRepository.findByNameAndMobile(name, mobile);
        if (user.isEmpty())
            return false;

        if (userCardRepository.findByUserIdAndCardId(user.get().getId(), card.getId()).isEmpty())
            userCardRepository.save(UserCard.builder()
                    .user(user.get())
                    .card(card).build());
        return true;
    }
}
