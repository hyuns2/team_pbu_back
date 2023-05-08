package projectbuildup.mivv.domain.archiving.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
import projectbuildup.mivv.domain.archiving.entity.CouponConditionCardEntity;
import projectbuildup.mivv.domain.archiving.entity.UserCardEntity;
import projectbuildup.mivv.domain.archiving.repository.CardRepository;
import projectbuildup.mivv.domain.archiving.repository.UserCardRepository;
import projectbuildup.mivv.domain.couponIssuance.entity.CouponIssuance;
import projectbuildup.mivv.domain.couponIssuance.repository.CouponIssuanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.imageStore.Image;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
import projectbuildup.mivv.global.error.exception.CCardNotFoundException;
import projectbuildup.mivv.global.error.exception.CInvalidCardConditionException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CouponArchivingService {

    private final CardRepository cardRepo;
    private final UserCardRepository userCardRepo;
    private final CouponIssuanceRepository couponIssuanceRepo;
    private final ImageUploader imageUploader;

    public void createCouponConditionCard(final ArchivingDto.createCouponCardRequestDto dto) throws IOException {

        // 조건이 하나도 안주어져 있는 경우
        if (dto.getWhatNumber() == null && dto.getHowSuccessive() == null) {
            throw new CInvalidCardConditionException();
        }

        Image image = imageUploader.upload(dto.getImage(), "cards");

        CouponConditionCardEntity entity = ArchivingDto.createCouponCardRequestDto.toEntity(dto, image.getImagePath());

        cardRepo.save(entity);

    }

    public void updateCouponConditionCard(final Long id, final ArchivingDto.updateCouponCardRequestDto dto) throws IOException {

        Optional<CouponConditionCardEntity> target = (Optional<CouponConditionCardEntity>)cardRepo.findById(id);
        if (target.isEmpty()) {
            throw new CCardNotFoundException();
        }

        Image image = imageUploader.upload(dto.getImage(), "cards");

        CouponConditionCardEntity result = target.get();
        result.updateCard(dto, image.getImagePath());

        cardRepo.save(result);

    }



}
