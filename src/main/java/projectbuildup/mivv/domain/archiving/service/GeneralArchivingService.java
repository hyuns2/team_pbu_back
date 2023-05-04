package projectbuildup.mivv.domain.archiving.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
import projectbuildup.mivv.domain.archiving.entity.CardEntity;
import projectbuildup.mivv.domain.archiving.entity.UserCardEntity;
import projectbuildup.mivv.domain.archiving.repository.CardRepository;
import projectbuildup.mivv.domain.archiving.repository.UserCardRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.common.fileUpload.FileStore;
import projectbuildup.mivv.global.common.fileUpload.UploadFile;
import projectbuildup.mivv.global.error.exception.CCardNotFoundException;
import projectbuildup.mivv.global.error.exception.CInvalidCellException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GeneralArchivingService {

    private final CardRepository cardRepo;
    private final UserCardRepository userCardRepo;
    private final UserRepository userRepo;

    public void createGeneralCard(final ArchivingDto.createGeneralCardRequestDto dto) {

        CardEntity entity = ArchivingDto.createGeneralCardRequestDto.toEntity(dto);
        cardRepo.save(entity);

    }

    public void updateGeneralCard(final Long id, final ArchivingDto.updateGeneralCardRequestDto dto) {

        Optional<CardEntity> target = cardRepo.findById(id);
        if (target.isEmpty()) {
            throw new CCardNotFoundException();
        }

        CardEntity result = target.get();
        result.updateCard(dto);

        cardRepo.save(result);

    }

    public void deleteCard(final Long id) {

        Optional<CardEntity> target = cardRepo.findById(id);
        if (target.isEmpty()) {
            throw new CCardNotFoundException();
        }

        CardEntity result = target.get();
        cardRepo.delete(result);

    }

    public ArchivingDto.CardResponseDto retrieveCard(final Long id) {

        Optional<CardEntity> target = cardRepo.findById(id);
        if (target.isEmpty()) {
            throw new CCardNotFoundException();
        }

        CardEntity result = target.get();
        return new ArchivingDto.CardResponseDto(result);

    }

    public List<ArchivingDto.CardResponseDto> retrieveCards() {

        List<CardEntity> result = cardRepo.findAll();

        return result.stream().map(ArchivingDto.CardResponseDto::new).collect(Collectors.toList());

    }

    public List<ArchivingDto.UserCardResponseDto> retrieveUserCards(final User user) {

        List<UserCardEntity> result = userCardRepo.findUserCardEntitiesByUser(user);

        return result.stream().map(ArchivingDto.UserCardResponseDto::new).collect(Collectors.toList());

    }

    void assignGeneralCards(CardEntity cardEntity, String name, String mobile) {
        // 이름과 전화번호로 사용자 알아내기
        Optional<User> targetUser = userRepo.findByNameAndMobile(name, mobile);
        if (targetUser.isEmpty()) {
            throw new CUserNotFoundException();
        }

        // 사용자와 카드 연결
        User userEntity = targetUser.get();
        userCardRepo.save(new UserCardEntity(userEntity, cardEntity, LocalDate.now()));
    }

    void checkAndAssignGeneralCards(MultipartFile dtoFile, CardEntity cardEntity) throws IOException {
        // 엑셀파일이면, 프로젝트 바로 안의 files 폴더에 저장
        FileStore fileStore = new FileStore();
        UploadFile file = fileStore.storeExcelFile(dtoFile);

        // 엑셀파일 읽어서, 이름과 전화번호 빼내기
        InputStream inputStream = new FileInputStream(fileStore.getFullPath(file.getStoreFilename()));
        Workbook workBook = WorkbookFactory.create(inputStream);
        Sheet sheet = workBook.getSheetAt(0);

        int totalRow = sheet.getPhysicalNumberOfRows();
        for (int rowIndex = 0; rowIndex < totalRow; rowIndex++) {
            Row row = sheet.getRow(rowIndex);

            if (row == null)
                throw new CInvalidCellException((rowIndex+1) + "행의 문제");

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

            assignGeneralCards(cardEntity, name, mobile);

        }
    }

    @Transactional
    public void assignGeneralCards(final ArchivingDto.AssignGeneralCardsRequestDto dto) throws IOException {

        // id에 맞는 카드 조회
        Optional<CardEntity> targetCard = cardRepo.findById(dto.getId());
        if (targetCard.isEmpty()) {
            throw new CCardNotFoundException();
        }
        CardEntity cardEntity = targetCard.get();

        checkAndAssignGeneralCards(dto.getFile(), cardEntity);

    }

}
