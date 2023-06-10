package projectbuildup.mivv.domain.archiving.service;

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
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.common.fileStore.FileUploader;
import projectbuildup.mivv.global.common.fileStore.File;
import projectbuildup.mivv.global.common.imageStore.ImageType;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
import projectbuildup.mivv.global.error.exception.CCardNotFoundException;
import projectbuildup.mivv.global.error.exception.CCardTypeNotMatchException;
import projectbuildup.mivv.global.error.exception.CInvalidCellException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;
import projectbuildup.mivv.global.common.imageStore.Image;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
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

    /**
     * 관리자가 일반 카드 생성
     * 
     * @param dto 카드 제목, 부제목, 명언, 이미지파일
     * @throws IOException
     */
    public void createGeneralConditionCard(final ArchivingDto.createOrUpdateGeneralCardRequestDto dto) throws IOException {

        Image image = imageUploader.upload(dto.getImage(), ImageType.CARD);

        CardEntity entity = ArchivingDto.createOrUpdateGeneralCardRequestDto.toEntity(dto, image.getImagePath());
        cardRepo.save(entity);

    }

    /**
     * 관리자가 일반 카드 수정
     *
     * @param id 일반 카드 Id
     * @param dto 카드 제목, 부제목, 명언, 이미지파일 중 수정항목
     * @throws IOException
     * @throws CCardNotFoundException 카드 찾기 실패시
     */
    @Transactional
    public void updateGeneralConditionCard(final Long id, final ArchivingDto.createOrUpdateGeneralCardRequestDto dto) throws IOException {

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
     * 관리자가 카드 삭제
     *
     * @param id 카드 Id
     * @throws CCardNotFoundException 카드 찾기 실패시
     */
    public void deleteCard(final Long id) {

        Optional<CardEntity> target = cardRepo.findById(id);
        if (target.isEmpty()) {
            throw new CCardNotFoundException();
        }

        CardEntity result = target.get();
        cardRepo.delete(result);

    }

    /**
     * 카드 단건 조회
     *
     * @param id 카드 Id
     * @return ArchivingDto.CardResponseDto 카드 정보 전체
     * @throws CCardNotFoundException 카드 찾기 실패시
     */
    public ArchivingDto.CardResponseDto retrieveCard(final Long id) {

        Optional<CardEntity> target = cardRepo.findById(id);
        if (target.isEmpty()) {
            throw new CCardNotFoundException();
        }

        CardEntity result = target.get();
        return new ArchivingDto.CardResponseDto(result);

    }

    /**
     * 카드 전체 조회
     *
     * @return List<ArchivingDto.CardResponseDto> 카드들의 정보 전체
     */
    public List<ArchivingDto.CardResponseDto> retrieveCards() {

        List<CardEntity> result = cardRepo.findAll();

        return result.stream().map(ArchivingDto.CardResponseDto::new).collect(Collectors.toList());

    }

    /**
     * 관리자가 일반 카드 할당
     *
     * @param dto 카드 Id, 엑셀파일
     * @throws IOException
     * @throws CCardNotFoundException 카드 찾기 실패시
     * @throws CInvalidCellException 엑셀의 셀이 유효하지 않을시
     * @throws CUserNotFoundException 유저 찾기 실패시
     */
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

    private void checkAndAssignGeneralConditionCards(MultipartFile dtoFile, CardEntity cardEntity) throws IOException {
        File file = fileUploader.storeExcelFile(dtoFile);
        ile.setReadable(true);

        InputStream inputStream = new FileInputStream(file.getFilePath());
        Workbook workBook = WorkbookFactory.create(inputStream);
        Sheet sheet = workBook.getSheetAt(0);

        int totalRow = sheet.getPhysicalNumberOfRows();
        for (int rowIndex = 0; rowIndex < totalRow; rowIndex++) {
            Row row = sheet.getRow(rowIndex);

            if (row == null)
                throw new CInvalidCellException((rowIndex+1) + "행의 문제");

            List<String> result = checkRowData(row, rowIndex);

            assignGeneralConditionCards(cardEntity, result.get(0), result.get(1));

        }
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

    private void assignGeneralConditionCards(CardEntity cardEntity, String name, String mobile) {
        Optional<User> targetUser = userRepo.findByNameAndMobile(name, mobile);
        if (targetUser.isEmpty()) {
            throw new CUserNotFoundException();
        }

        User userEntity = targetUser.get();
        userCardRepo.save(new UserCardEntity(userEntity, cardEntity, LocalDate.now()));
    }


    /**
     * 사용자의 새로 부여받은 카드들 조회
     *
     * @param user 유저 정보
     * @return List<ArchivingDto.UserCardResponseDto> 유저카드들 정보 전체
     */
    public List<ArchivingDto.UserCardResponseDto> retrieveUserNewCards(final User user) {

        List<UserCardEntity> result = userCardRepo.findUserNewCards(user);

        return result.stream().map(ArchivingDto.UserCardResponseDto::new).collect(Collectors.toList());

    }

    /**
     * 사용자의 뉴카드를 1에서 0으로
     *
     * @param user 유저 정보
     */
    @Transactional
    public void updateUserNewCards(final User user) {

        List<UserCardEntity> result = userCardRepo.findUserNewCards(user);

        for (UserCardEntity entity: result) {
            entity.updateIsNew();
        }

    }

    /**
     * 사용자가 보유한 카드라면 그 정보까지 카드 전체 정보를 반환
     *
     * @param user 유저 정보
     * @param cardType 카드 타입
     * @return List<ArchivingDto.CardAndUserCardResponseDto> 카드와 사용자카드 정보 전체
     */
    public List<ArchivingDto.CardAndUserCardResponseDto> retrieveUserCards(final User user, final CardType cardType) {

        List<Object[]> userGeneralCards = cardRepo.findUserGeneralCards(user, cardType);
        List<ArchivingDto.CardAndUserCardResponseDto> dtos = new ArrayList<ArchivingDto.CardAndUserCardResponseDto>();

        for (Object[] userGeneralCard: userGeneralCards) {
            ArchivingDto.CardAndUserCardResponseDto dto;

            if (userGeneralCard[1] == null)
                dto = new ArchivingDto.CardAndUserCardResponseDto((CardEntity) userGeneralCard[0]);
            else
                dto = new ArchivingDto.CardAndUserCardResponseDto((CardEntity) userGeneralCard[0], (UserCardEntity) userGeneralCard[1]);

            dtos.add(dto);
        }

        return dtos;

    }

}
