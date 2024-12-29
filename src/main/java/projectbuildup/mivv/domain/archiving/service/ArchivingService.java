package projectbuildup.mivv.domain.archiving.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.mivv.domain.archiving.entity.CardType;
import projectbuildup.mivv.domain.archiving.entity.CouponCard;
import projectbuildup.mivv.domain.archiving.entity.SavingCard;
import projectbuildup.mivv.domain.archiving.entity.UserCard;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.couponIssuance.entity.CouponIssuance;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.error.exception.CCouponNotFoundException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArchivingService {
//    /**
//     * 절약 카드 발급
//     *
//     * @param user 유저 정보
//     */
//    @Transactional
//    public void assignSavingCards(User user) {
//        List<SavingCard> checkedCards = getCheckedSavingCardList(user);
//
//        assignSavingCard(user, checkedCards);
//    }
//
//    private List<SavingCard> getCheckedSavingCardList(User user) {
//        List<UserCard> alreadyExistings = userCardRepo.findUserCardEntitiesByUser(user);
//        List<SavingCard> allCards = cardRepository.findAllByType(CardType.SAVING);
//
//        for (UserCard element: alreadyExistings) {
//            allCards.remove(element.getCard());
//        }
//        return allCards;
//    }
//
//    private void assignSavingCard(User user, List<SavingCard> checkedCards) {
//        for (SavingCard element: checkedCards) {
//            Integer charge = element.getCharge();
//            Integer count = element.getCount();
//            Integer term = element.getTerm();
//
//            if (term == 0)
//                ifNoHaveTerm(user, charge, count, element);
//            else
//                ifHaveTerm(user, charge, count, term, element);
//        }
//    }
//
//    private void ifNoHaveTerm(User user, Integer charge, Integer count, SavingCard element) {
//        Integer chargeSum = remittanceRepo.findChargeSum(user);
//        Integer countSum = remittanceRepo.findCountSum(user);
//
//        if (chargeSum >= charge && countSum >= count)
//            userCardRepo.save(new UserCard(user, element));
//    }
//
//    private void ifHaveTerm(User user, Integer charge, Integer count, Integer term, SavingCard element) {
//        Integer chargeSum = remittanceRepo.findChargeSumBetweenTerm(user, LocalDateTime.now().minusDays(term), LocalDateTime.now());
//        Integer countSum = remittanceRepo.findCountSumBetweenTerm(user, LocalDateTime.now().minusDays(term), LocalDateTime.now());
//
//        if (chargeSum >= charge && countSum >= count)
//            userCardRepo.save(new UserCard(user, element));
//    }
//
//    /**
//     * 소비 카드 발급
//     *
//     * @param user     유저 정보
//     * @param couponId 쿠폰 Id
//     * @throws CCouponNotFoundException 쿠폰 찾기 실패시
//     */
//    @Transactional
//    public void assignCouponCards(final User user, final Long couponId) {
//        Optional<Coupon> target = couponRepo.findById(couponId);
//        if (target.isEmpty()) {
//            throw new CCouponNotFoundException();
//        }
//        Coupon coupon = target.get();
//
//        int whatNumber = checkWhatNumber(user, coupon);
//        int howSuccessive = checkHowSuccessive(user);
//
//        assignSavingCard(user, whatNumber, howSuccessive);
//    }
//
//    private int checkWhatNumber(User user, Coupon coupon) {
//        List<CouponIssuance> issuancesByCouponId = couponIssuanceRepo.findAllByCoupon(coupon);
//        int whatNumber = 0;
//        for (CouponIssuance element : issuancesByCouponId) {
//            if (isEqualUserId(element, user))
//                whatNumber = issuancesByCouponId.indexOf(element) + 1;
//        }
//        return whatNumber;
//    }
//
//    private boolean isEqualUserId(CouponIssuance element, User user) {
//        return (element.getUser().getId().equals(user.getId()));
//    }
//
//    private int checkHowSuccessive(User user) {
//        List<LocalDateTime> createdTimesByUserId = couponIssuanceRepo.findCreatedTimeByUserId(user);
//        LocalDateTime before = LocalDateTime.now();
//        int howSuccessive = 1;
//
//        for (LocalDateTime element : createdTimesByUserId) {
//            if (isNotAssignedInThisMonth(createdTimesByUserId, element))
//                return 0;
//
//            long diffMonths = Math.abs(ChronoUnit.MONTHS.between(before, element));
//            if (diffMonths == 1)
//                howSuccessive++;
//            else if (diffMonths > 1)
//                break;
//
//            before = element;
//        }
//        return howSuccessive;
//    }
//
//    private boolean isNotAssignedInThisMonth(List<LocalDateTime> createdTimesByUserId, LocalDateTime element) {
//        return (createdTimesByUserId.indexOf(element) == 0 && Math.abs(ChronoUnit.MONTHS.between(LocalDateTime.now(), element)) > 0);
//    }
//
//    private void assignSavingCard(User user, int whatNumber, int howSuccessive) {
//        List<CouponCard> checkedcards = getCheckedCards(user);
//
//        for (CouponCard element : checkedcards) {
//            if (UnSatisfiedHowSuccessive(element, howSuccessive))
//                continue;
//            if (UnsatisfiedWhatNumber(element, whatNumber))
//                continue;
//
//            userCardRepo.save(new UserCard(user, element));
//        }
//    }
//
//    private List<CouponCard> getCheckedSavingCardList(User user) {
//        List<UserCard> alreadyExistings = userCardRepo.findUserCardEntitiesByUser(user);
//        List<CouponCard> allCards = cardRepository.findAllByType(CardType.COUPON);
//        for (UserCard element : alreadyExistings) {
//            allCards.remove(element.getCard());
//        }
//        return allCards;
//    }
//
//    private boolean UnSatisfiedHowSuccessive(CouponCard element, int howSuccessive) {
//        return (element.getHowSuccessive() > howSuccessive);
//    }
//
//    private boolean UnsatisfiedWhatNumber(CouponCard element, int whatNumber) {
//        return (element.getWhatNumber() != 0 && element.getWhatNumber() != whatNumber);
//    }
}
