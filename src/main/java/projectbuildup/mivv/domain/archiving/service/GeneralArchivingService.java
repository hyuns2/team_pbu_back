package projectbuildup.mivv.domain.archiving.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
import projectbuildup.mivv.domain.archiving.entity.CardEntity;
import projectbuildup.mivv.domain.archiving.entity.UserCardEntity;
import projectbuildup.mivv.domain.archiving.repository.CardRepository;
import projectbuildup.mivv.domain.archiving.repository.UserCardRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.fileUpload.FileStore;
import projectbuildup.mivv.global.common.fileUpload.UploadFile;
import projectbuildup.mivv.global.error.exception.CCardNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GeneralArchivingService {

    private final CardRepository cardRepo;
    private final UserCardRepository userCardRepo;

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

    public void assignGeneralCards(final ArchivingDto.AssignGeneralCardsRequestDto dto) throws IOException {

        FileStore fileStore = new FileStore();
        UploadFile file = fileStore.storeFile(dto.getFile());

        // 확장자 엑셀 아니면 쳐내기
        // 엑셀파일 읽고 사용자에게 카드 할당

    }

}
