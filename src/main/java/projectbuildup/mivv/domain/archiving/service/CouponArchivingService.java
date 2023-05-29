package projectbuildup.mivv.domain.archiving.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
import projectbuildup.mivv.domain.archiving.entity.CouponConditionCardEntity;
import projectbuildup.mivv.domain.archiving.entity.UserCardEntity;
import projectbuildup.mivv.domain.archiving.repository.CardRepository;
import projectbuildup.mivv.domain.archiving.repository.UserCardRepository;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.coupon.repository.CouponRepository;
import projectbuildup.mivv.domain.couponIssuance.entity.CouponIssuance;
import projectbuildup.mivv.domain.couponIssuance.repository.CouponIssuanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.imageStore.Image;
import projectbuildup.mivv.global.common.imageStore.ImageType;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
import projectbuildup.mivv.global.error.exception.CCardNotFoundException;
import projectbuildup.mivv.global.error.exception.CCouponNotFoundException;
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
    private final CouponRepository couponRepo;
    private final ImageUploader imageUploader;

    public void createCouponConditionCard(final ArchivingDto.createCouponCardRequestDto dto) throws IOException {

        // 조건이 하나도 안주어져 있는 경우
        if (dto.getWhatNumber() == 0 && dto.getHowSuccessive() == 0) {
            throw new CInvalidCardConditionException();
        }

        Image image = imageUploader.upload(dto.getImage(), ImageType.CARD);

        CouponConditionCardEntity entity = ArchivingDto.createCouponCardRequestDto.toEntity(dto, image.getImagePath());

        cardRepo.save(entity);

    }

    public void updateCouponConditionCard(final Long id, final ArchivingDto.updateCouponCardRequestDto dto) throws IOException {

        Optional<CouponConditionCardEntity> target = (Optional<CouponConditionCardEntity>) cardRepo.findById(id);
        if (target.isEmpty()) {
            throw new CCardNotFoundException();
        }

        Image image = imageUploader.upload(dto.getImage(), ImageType.CARD);

        CouponConditionCardEntity result = target.get();
        result.updateCard(dto, image.getImagePath());

        cardRepo.save(result);

    }

    private void assignCards(User user, int whatNumber, int howSuccessive) {
        List<UserCardEntity> alreadyExistings = userCardRepo.findUserCardEntitiesByUser(user);

        List<CouponConditionCardEntity> allCards = (List<CouponConditionCardEntity>) cardRepo.findAll();

        for (UserCardEntity element : alreadyExistings) {
            allCards.remove(element.getCardEntity());
        }

        List<CouponConditionCardEntity> cardsToCheck = allCards;

        for (CouponConditionCardEntity element : cardsToCheck) {
            if (element.getWhatNumber() != whatNumber && element.getHowSuccessive() > howSuccessive)
                continue;

            userCardRepo.save(new UserCardEntity(user, element, LocalDate.now()));
        }
    }

    private int checkHowSuccessive(User user) {
        int howSuccessive = 0;

        List<LocalDateTime> createdTimesByUserId = couponIssuanceRepo.findCreatedTimeByUserId(user);
        LocalDateTime before = LocalDateTime.now();
        for (LocalDateTime element : createdTimesByUserId) {
            // 마지막 발급이 이번달인가?
            if (createdTimesByUserId.indexOf(element) == 0 && Math.abs(ChronoUnit.MONTHS.between(LocalDateTime.now(), element)) > 0)
                break;

            long diffMonths = ChronoUnit.MONTHS.between(before, element);
            diffMonths = Math.abs(diffMonths);
            if (diffMonths == 1) {
                howSuccessive++;
            } else if (diffMonths > 1) {
                break;
            }

            before = element;
        }
        return howSuccessive;
    }

    private int checkWhatNumber(User user, Coupon coupon) {
        List<CouponIssuance> issuancesByCouponId = couponIssuanceRepo.findAllByCoupon(coupon);
        int whatNumber = 0;
        for (CouponIssuance element : issuancesByCouponId) {
            if (element.getUser().getId().equals(user.getId())) {
                whatNumber = issuancesByCouponId.indexOf(element) + 1;
                break;
            }
        }
        return whatNumber;
    }

    /**
     * sdjflsjdlf벨라 바보
     *
     * @param user     사용자
     * @param couponId 쿠폰 아이디
     */
    @Transactional
    public void assignCouponConditionsCard(final User user, final Long couponId) {

        // 쿠폰 객체 불러오기
        Optional<Coupon> target = couponRepo.findById(couponId);
        if (target.isEmpty()) {
            throw new CCouponNotFoundException();
        }
        Coupon coupon = target.get();

        // 해당 쿠폰에서 몇번째 발급자에 해당하는지 체크
        int whatNumber = checkWhatNumber(user, coupon);

        // 몇달째 연속으로 발급했는지 체크
        int howSuccessive = checkHowSuccessive(user);

        // 쿠폰 발급 카드 조건 사항에 맞다면 카드 부여
        assignCards(user, whatNumber, howSuccessive);

    }

}
