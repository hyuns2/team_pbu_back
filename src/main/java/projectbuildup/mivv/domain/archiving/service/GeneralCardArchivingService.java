package projectbuildup.mivv.domain.archiving.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
import projectbuildup.mivv.domain.archiving.entity.CardEntity;
import projectbuildup.mivv.domain.archiving.entity.CardType;
import projectbuildup.mivv.domain.archiving.entity.UserCardEntity;
import projectbuildup.mivv.domain.archiving.repository.CardRepository;
import projectbuildup.mivv.domain.archiving.repository.UserCardRepository;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.common.fileStore.ExcelManager;
import projectbuildup.mivv.global.common.fileStore.File;
import projectbuildup.mivv.global.common.imageStore.ImageType;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
import projectbuildup.mivv.global.error.exception.CCardNotFoundException;
import projectbuildup.mivv.global.error.exception.CCardTypeNotMatchException;
import projectbuildup.mivv.global.error.exception.CInvalidCellException;
import projectbuildup.mivv.global.common.imageStore.Image;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GeneralCardArchivingService {

    private final CardRepository cardRepo;
    private final UserCardRepository userCardRepo;
    private final UserRepository userRepo;

    private final ImageUploader imageUploader;
    private final ExcelManager excelManager;

    /**
     * 관리자가 일반 카드 생성
     *
     * @param dto 카드 제목, 부제목, 명언, 이미지파일
     */
    public void createGeneralCard(final ArchivingDto.createOrUpdateGeneralCardRequestDto dto) throws IOException {
        Image image = imageUploader.upload(dto.getImage(), ImageType.CARD);

        CardEntity entity = ArchivingDto.createOrUpdateGeneralCardRequestDto.toEntity(dto, image.getImagePath());
        cardRepo.save(entity);
    }

    /**
     * 관리자가 일반 카드 수정
     *
     * @param id 일반 카드 Id
     * @param dto 카드 제목, 부제목, 명언, 이미지파일 중 수정항목
     * @throws CCardNotFoundException 카드 찾기 실패시
     */
    @Transactional
    public void updateGeneralCard(final Long id, final ArchivingDto.createOrUpdateGeneralCardRequestDto dto) throws IOException {
        Optional<CardEntity> target = cardRepo.findById(id);
        if (target.isEmpty()) {
            throw new CCardNotFoundException();
        }
        if (!target.get().getType().equals(CardType.GENERAL)) {
            throw new CCardTypeNotMatchException();
        }

        CardEntity result = target.get();
        Image image = imageUploader.upload(dto.getImage(), ImageType.CARD);
        result.updateCard(dto, image.getImagePath());
    }

    /**
     * 관리자가 일반 카드 할당
     *
     * @param dto 카드 Id, 엑셀파일
     * @throws CCardNotFoundException 카드 찾기 실패시
     * @throws CInvalidCellException 엑셀의 셀이 유효하지 않을시
     */
    @Transactional
    public void assignCards(final ArchivingDto.AssignGeneralCardsRequestDto dto, final HttpServletResponse response) throws IOException {
        Optional<CardEntity> targetCard = cardRepo.findById(dto.getId());
        if (targetCard.isEmpty()) {
            throw new CCardNotFoundException();
        }
        if (!targetCard.get().getType().equals(CardType.GENERAL)) {
            throw new CCardTypeNotMatchException();
        }

        CardEntity cardEntity = targetCard.get();
        checkAndAssignGeneralCards(dto.getFile(), cardEntity, response);
    }

    private void checkAndAssignGeneralCards(MultipartFile dtoFile, CardEntity cardEntity, HttpServletResponse response) throws IOException {
        File file = excelManager.storeExcelFile(dtoFile);
        InputStream inputStream = new FileInputStream(file.getFilePath());

        Workbook workBook = WorkbookFactory.create(inputStream);
        Sheet sheet = workBook.getSheetAt(0);

        List<User> notFoundUsers = new ArrayList<>();

        int totalRow = sheet.getPhysicalNumberOfRows();
        for (int rowIndex = 0; rowIndex < totalRow; rowIndex++) {
            Row row = sheet.getRow(rowIndex);

            if (row == null)
                throw new CInvalidCellException((rowIndex+1) + "행의 문제");

            List<String> result = checkRowData(row, rowIndex);

            assignCards(cardEntity, result.get(0), result.get(1), notFoundUsers);
        }

        excelManager.writeExcel(response, notFoundUsers, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    private List<String> checkRowData(Row row, int rowIndex) {
        String name = null;
        String mobile = null;
        for (int cellIndex = 0; cellIndex < 2; cellIndex++) {
            Cell cell = row.getCell(cellIndex);

            if (cell == null || cell.getCellType() != CellType.STRING)
                throw new CInvalidCellException((rowIndex+1) + "행의 문제");

            if (cellIndex == 0)
                name = cell.getStringCellValue();
            else
                mobile = cell.getStringCellValue();
        }

        List<String> result = new ArrayList<>(2);
        result.add(0, name);
        result.add(1, mobile);

        return result;
    }

    private void assignCards(CardEntity cardEntity, String name, String mobile, List<User> notFoundUsers) {
        Optional<User> targetUser = userRepo.findByNameAndMobile(name, mobile);
        if (targetUser.isEmpty()) {
            IdentityVerification identityVerification = new IdentityVerification();
            identityVerification.setNameAndMobile(name, mobile);
            User notFoundUser = new User();
            notFoundUser.setIdentityVerification(identityVerification);
            notFoundUsers.add(notFoundUser);
        }
        else {
            User userEntity = targetUser.get();
            userCardRepo.save(new UserCardEntity(userEntity, cardEntity, LocalDate.now()));
        }
    }

}
