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
import projectbuildup.mivv.global.common.fileStore.FileUploader;
import projectbuildup.mivv.global.common.fileStore.File;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
import projectbuildup.mivv.global.error.exception.CCardNotFoundException;
import projectbuildup.mivv.global.error.exception.CInvalidCellException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;
import projectbuildup.mivv.global.common.imageStore.Image;

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

    private final ImageUploader imageUploader;
    private final FileUploader fileUploader;

    public void createGeneralConditionCard(final ArchivingDto.createGeneralCardRequestDto dto) throws IOException {

        Image image = imageUploader.upload(dto.getImage(), "cards");

        CardEntity entity = ArchivingDto.createGeneralCardRequestDto.toEntity(dto, image.getImagePath());
        cardRepo.save(entity);

    }

    public void updateGeneralConditionCard(final Long id, final ArchivingDto.updateGeneralCardRequestDto dto) throws IOException {

        Optional<CardEntity> target = cardRepo.findById(id);
        if (target.isEmpty()) {
            throw new CCardNotFoundException();
        }

        Image image = imageUploader.upload(dto.getImage(), "cards");

        CardEntity result = target.get();
        result.updateCard(dto, image.getImagePath());

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

    private void assignGeneralConditionCards(CardEntity cardEntity, String name, String mobile) {
        // 이름과 전화번호로 사용자 알아내기
        Optional<User> targetUser = userRepo.findByNameAndMobile(name, mobile);
        if (targetUser.isEmpty()) {
            throw new CUserNotFoundException();
        }

        // 사용자와 카드 연결
        User userEntity = targetUser.get();
        userCardRepo.save(new UserCardEntity(userEntity, cardEntity, LocalDate.now()));
    }



    private void checkAndAssignGeneralConditionCards(MultipartFile dtoFile, CardEntity cardEntity) throws IOException {
        // 엑셀파일이면, 프로젝트 바로 안의 files 폴더에 저장
        File file = fileUploader.storeExcelFile(dtoFile);

        // 엑셀파일 읽어서, 이름과 전화번호 빼내기
        InputStream inputStream = new FileInputStream(file.getFilePath());
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

            assignGeneralConditionCards(cardEntity, name, mobile);

        }
    }

    @Transactional
    public void assignGeneralConditionCards(final ArchivingDto.AssignGeneralCardsRequestDto dto) throws IOException {

        // id에 맞는 카드 조회
        Optional<CardEntity> targetCard = cardRepo.findById(dto.getId());
        if (targetCard.isEmpty()) {
            throw new CCardNotFoundException();
        }
        CardEntity cardEntity = targetCard.get();

        checkAndAssignGeneralConditionCards(dto.getFile(), cardEntity);

    }

}
