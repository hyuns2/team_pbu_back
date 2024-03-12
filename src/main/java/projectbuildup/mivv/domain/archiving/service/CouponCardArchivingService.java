package projectbuildup.mivv.domain.archiving.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
import projectbuildup.mivv.domain.archiving.entity.CardType;
import projectbuildup.mivv.domain.archiving.entity.CouponCardEntity;
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
import projectbuildup.mivv.global.error.exception.CCardTypeNotMatchException;
import projectbuildup.mivv.global.error.exception.CCouponNotFoundException;
import projectbuildup.mivv.global.error.exception.CInvalidCardConditionException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponCardArchivingService {

    private final CardRepository<CouponCardEntity> cardRepo;
    private final UserCardRepository userCardRepo;
    private final CouponIssuanceRepository couponIssuanceRepo;
    private final CouponRepository couponRepo;
    private final ImageUploader imageUploader;

    /**
     * 소비 카드 생성
     *
     * @param dto 소비 정보, 발급 조건
     * @throws CInvalidCardConditionException 카드 조건이 없을 시
     */
    public void createCouponCard(final ArchivingDto.createOrUpdateCouponCardRequestDto dto) throws IOException {
        if (NoAnyConditions(dto)) {
            throw new CInvalidCardConditionException();
        }

        Image image = imageUploader.upload(dto.getImage(), ImageType.CARD);
        CouponCardEntity entity = ArchivingDto.createOrUpdateCouponCardRequestDto.toEntity(dto, image.getImagePath());
        cardRepo.save(entity);
    }

    private boolean NoAnyConditions(ArchivingDto.createOrUpdateCouponCardRequestDto dto) {
        return (dto.getWhatNumber() == 0 && dto.getHowSuccessive() == 0);
    }

    /**
     * 소비 카드 수정
     *
     * @param id 카드 Id
     * @param dto 수정하려는 정보
     * @throws CCardNotFoundException 카드 찾기 실패시
     */
    @Transactional
    public void updateCouponCard(final Long id, final ArchivingDto.createOrUpdateCouponCardRequestDto dto) throws IOException {
        if (NoAnyConditions(dto)) {
            throw new CInvalidCardConditionException();
        }

        Optional<CouponCardEntity> target = cardRepo.findById(id);
        if (target.isEmpty()) {
            throw new CCardNotFoundException();
        }
        if (!target.get().getType().equals(CardType.COUPON)) {
            throw new CCardTypeNotMatchException();
        }

        CouponCardEntity result = target.get();
        Image image = imageUploader.upload(dto.getImage(), ImageType.CARD);
        result.updateCard(dto, image.getImagePath());
    }

    /**
     * 소비 카드 발급
     *
     * @param user     유저 정보
     * @param couponId 쿠폰 Id
     * @throws CCouponNotFoundException 쿠폰 찾기 실패시
     */
    @Transactional
    public void assignCouponConditionsCard(final User user, final Long couponId) {
        Optional<Coupon> target = couponRepo.findById(couponId);
        if (target.isEmpty()) {
            throw new CCouponNotFoundException();
        }
        Coupon coupon = target.get();

        int whatNumber = checkWhatNumber(user, coupon);
        int howSuccessive = checkHowSuccessive(user);

        assignCards(user, whatNumber, howSuccessive);
    }

    private int checkWhatNumber(User user, Coupon coupon) {
        List<CouponIssuance> issuancesByCouponId = couponIssuanceRepo.findAllByCoupon(coupon);
        int whatNumber = 0;
        for (CouponIssuance element : issuancesByCouponId) {
            if (isEqualUserId(element, user))
                whatNumber = issuancesByCouponId.indexOf(element) + 1;
        }
        return whatNumber;
    }

    private boolean isEqualUserId(CouponIssuance element, User user) {
        return (element.getUser().getId().equals(user.getId()));
    }

    private int checkHowSuccessive(User user) {
        List<LocalDateTime> createdTimesByUserId = couponIssuanceRepo.findCreatedTimeByUserId(user);
        LocalDateTime before = LocalDateTime.now();
        int howSuccessive = 1;

        for (LocalDateTime element : createdTimesByUserId) {
            if (isNotAssignedInThisMonth(createdTimesByUserId, element))
                return 0;

            long diffMonths = Math.abs(ChronoUnit.MONTHS.between(before, element));
            if (diffMonths == 1)
                howSuccessive++;
            else if (diffMonths > 1)
                break;

            before = element;
        }
        return howSuccessive;
    }

    private boolean isNotAssignedInThisMonth(List<LocalDateTime> createdTimesByUserId, LocalDateTime element) {
        return (createdTimesByUserId.indexOf(element) == 0 && Math.abs(ChronoUnit.MONTHS.between(LocalDateTime.now(), element)) > 0);
    }

    private void assignCards(User user, int whatNumber, int howSuccessive) {
        List<CouponCardEntity> checkedcards = getCheckedCards(user);

        for (CouponCardEntity element : checkedcards) {
            if (UnSatisfiedHowSuccessive(element, howSuccessive))
                continue;
            if (UnsatisfiedWhatNumber(element, whatNumber))
                continue;

            userCardRepo.save(new UserCardEntity(user, element, LocalDate.now()));
        }
    }

    private List<CouponCardEntity> getCheckedCards(User user) {
        List<UserCardEntity> alreadyExistings = userCardRepo.findUserCardEntitiesByUser(user);
        List<CouponCardEntity> allCards = cardRepo.findAllByType(CardType.COUPON);
        for (UserCardEntity element : alreadyExistings) {
            allCards.remove(element.getCardEntity());
        }
        return allCards;
    }

    private boolean UnSatisfiedHowSuccessive(CouponCardEntity element, int howSuccessive) {
        return (element.getHowSuccessive() > howSuccessive);
    }

    private boolean UnsatisfiedWhatNumber(CouponCardEntity element, int whatNumber) {
        return (element.getWhatNumber() != 0 && element.getWhatNumber() != whatNumber);
    }

}
