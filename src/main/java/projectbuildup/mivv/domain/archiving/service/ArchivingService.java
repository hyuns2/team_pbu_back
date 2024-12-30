package projectbuildup.mivv.domain.archiving.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.mivv.domain.archiving.entity.*;
import projectbuildup.mivv.domain.archiving.repository.CardRepository;
import projectbuildup.mivv.domain.archiving.repository.UserCardRepository;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.couponIssuance.entity.CouponIssuance;
import projectbuildup.mivv.domain.couponIssuance.repository.CouponIssuanceRepository;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArchivingService {
    private final CardRepository<Card> cardRepository;
    private final UserCardRepository userCardRepository;
    private final RemittanceRepository remittanceRepository;
    private final CouponIssuanceRepository couponIssuanceRepository;

    /**
     * 절약카드 발급
     *
     * @param user 유저 정보
     */
    @Transactional
    public void assignSavingCards(User user) {
        List<SavingCard> notHavingCardList = cardRepository.findNotHavingCardsByUserAndTypeCond(user, CardType.SAVING)
                .stream().map((c) -> (SavingCard)c).toList();

        for (SavingCard card: notHavingCardList) {
            Integer charge = card.getCharge();
            Integer count = card.getCount();
            Integer term = card.getTerm();

            if (term == 0)
                assignNoTermCard(user, card, charge, count);
            else
                assignTermCard(user, card, charge, count, term);
        }
    }

    private void assignNoTermCard(User user, SavingCard card, Integer charge, Integer count) {
        Integer chargeSum = remittanceRepository.findChargeSum(user);
        Integer countSum = remittanceRepository.findCountSum(user);

        if (chargeSum >= charge && countSum >= count)
            userCardRepository.save(UserCard.of(user, card));
    }

    private void assignTermCard(User user, SavingCard card, Integer charge, Integer count, Integer term) {
        Integer chargeSum = remittanceRepository.findChargeSumBetweenTerm(user, LocalDateTime.now().minusDays(term), LocalDateTime.now());
        Integer countSum = remittanceRepository.findCountSumBetweenTerm(user, LocalDateTime.now().minusDays(term), LocalDateTime.now());

        if (chargeSum >= charge && countSum >= count)
            userCardRepository.save(UserCard.of(user, card));
    }

    /**
     * 소비카드 발급
     *
     * @param user 유저 정보
     * @param coupon 쿠폰 정보
     */
    @Transactional
    public void assignCouponCards(User user, Coupon coupon) {
        int whatNumber = getWhatNumber(user, coupon);
        int howSuccessive = getHowSuccessive(user);

        List<CouponCard> notHavingCardList = cardRepository.findNotHavingCardsByUserAndTypeCond(user, CardType.COUPON)
                .stream().map((c) -> (CouponCard)c).toList();
        for (CouponCard card: notHavingCardList) {
            if (unSatisfiedHowSuccessive(card, howSuccessive) || UnsatisfiedWhatNumber(card, whatNumber))
                continue;

            userCardRepository.save(UserCard.of(user, card));
        }
    }

    private int getWhatNumber(User user, Coupon coupon) {
        List<CouponIssuance> issuanceList = couponIssuanceRepository.findAllByCoupon(coupon);
        for (CouponIssuance issuance: issuanceList) {
            if (issuance.getUser().equals(user))
                return issuanceList.indexOf(issuance) + 1;
        }
        return 0;
    }

    private int getHowSuccessive(User user) {
        List<LocalDateTime> createdTimeList = couponIssuanceRepository.findCreatedTimeByUserId(user);
        if (isNotIssuedInThisMonth(createdTimeList))
            return 0;

        int howSuccessive = 1;
        LocalDateTime after = LocalDateTime.now();
        for (LocalDateTime createdTime : createdTimeList) {
            if (Math.abs(ChronoUnit.MONTHS.between(createdTime, after)) != 1)
                break;

            howSuccessive++;
            after = createdTime;
        }
        return howSuccessive;
    }

    private boolean isNotIssuedInThisMonth(List<LocalDateTime> createdTimeList) {
        return Math.abs(ChronoUnit.MONTHS.between(LocalDateTime.now(), createdTimeList.get(0))) != 0;
    }

    private boolean unSatisfiedHowSuccessive(CouponCard card, int howSuccessive) {
        return (card.getHowSuccessive() > howSuccessive);
    }

    private boolean UnsatisfiedWhatNumber(CouponCard card, int whatNumber) {
        return (card.getWhatNumber() != 0 && card.getWhatNumber() != whatNumber);
    }
}
