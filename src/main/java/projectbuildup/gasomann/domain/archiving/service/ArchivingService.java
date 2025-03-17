package projectbuildup.gasomann.domain.archiving.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.gasomann.domain.archiving.entity.*;
import projectbuildup.gasomann.domain.archiving.repository.CardRepository;
import projectbuildup.gasomann.domain.archiving.repository.UserCardRepository;
import projectbuildup.gasomann.domain.coupon.entity.Coupon;
import projectbuildup.gasomann.domain.couponIssuance.entity.CouponIssuance;
import projectbuildup.gasomann.domain.couponIssuance.repository.CouponIssuanceRepository;
import projectbuildup.gasomann.domain.remittance.repository.RemittanceRepository;
import projectbuildup.gasomann.domain.remittance.repository.ReportForCard;
import projectbuildup.gasomann.domain.user.entity.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArchivingService {
    private final CardRepository<Card> cardRepository;
    private final UserCardRepository userCardRepository;
    private final RemittanceRepository remittanceRepository;
    private final CouponIssuanceRepository couponIssuanceRepository;

    /**
     * 절약카드 발급
     */
    @Transactional
    public void assignSavingCards(User user) {
        List<UserCard> userCards = new ArrayList<>();
        cardRepository.findNotHavingCardsByUserAndTypeCond(user, CardType.SAVING)
                .forEach(card -> {
                    SavingCard savingCard = (SavingCard) card;
                    if ((savingCard.getTerm() == 0 && checkNoTermCard(user, savingCard))
                            || (savingCard.getTerm() != 0 && checkTermCard(user, savingCard)))
                        userCards.add(UserCard.of(user, savingCard));
                });

        userCardRepository.saveAll(userCards);
    }

    private boolean checkNoTermCard(User user, SavingCard card) {
        ReportForCard report = remittanceRepository.findChargeSumAndCountSumByUser(user);

        return (report.getChargeSum() >= card.getCharge() && report.getCountSum() >= card.getCount());
    }

    private boolean checkTermCard(User user, SavingCard savingCard) {
        ReportForCard report = remittanceRepository.findChargeSumAndCountSumByUserAndTermBetween(user,
                LocalDateTime.now().minusDays(savingCard.getTerm()), LocalDateTime.now());

        return (report.getChargeSum() >= savingCard.getCharge() && report.getCountSum() >= savingCard.getCount());
    }

    /**
     * 소비카드 발급
     */
    @Transactional
    public void assignCouponCards(User user, Coupon coupon) {
        int couponIssueOrder = getCouponIssueOrderByUserCoupon(user, coupon);
        int continuousIssueMonths = getContinuousIssueMonthsByUserCoupon(user);

        List<UserCard> userCards = new ArrayList<>();
        cardRepository.findNotHavingCardsByUserAndTypeCond(user, CardType.COUPON)
                .forEach(card -> {
                    CouponCard couponCard = (CouponCard) card;
                    boolean isEqualToCouponIssueOrder = checkCouponIssueOrder(couponCard, couponIssueOrder);
                    boolean isEqualToContinuousIssueMonths = checkContinuousIssueMonths(couponCard, continuousIssueMonths);
                    if ((isEqualToCouponIssueOrder && isEqualToContinuousIssueMonths)
                        || (isEqualToCouponIssueOrder && couponCard.getContinuousIssueMonths() == 0)
                        || (couponCard.getCouponIssueOrder() == 0 && isEqualToContinuousIssueMonths)
                        || (couponCard.getCouponIssueOrder() == 0 && couponCard.getContinuousIssueMonths() == 0))
                        userCards.add(UserCard.of(user, couponCard));
                });

        userCardRepository.saveAll(userCards);
    }

    private int getCouponIssueOrderByUserCoupon(User user, Coupon coupon) {
        List<CouponIssuance> issuanceList = couponIssuanceRepository.findAllByCoupon(coupon);
        for (CouponIssuance issuance: issuanceList) {
            if (issuance.getUser().equals(user))
                return issuanceList.indexOf(issuance) + 1;
        }
        return 0;
    }

    private int getContinuousIssueMonthsByUserCoupon(User user) {
        List<LocalDateTime> createdTimes = couponIssuanceRepository.findCreatedTimesByUser(user);

        int howSuccessive = 1;
        LocalDateTime current = LocalDateTime.now();
        for (LocalDateTime createdTime : createdTimes) {
            if (Math.abs(ChronoUnit.MONTHS.between(createdTime, current)) > 1)
                break;

            howSuccessive++;
            current = createdTime;
        }
        return howSuccessive;
    }

    private boolean checkCouponIssueOrder(CouponCard couponCard, int couponIssueOrder) {
        return (couponCard.getCouponIssueOrder() != 0 && couponCard.getCouponIssueOrder() == couponIssueOrder);
    }

    private boolean checkContinuousIssueMonths(CouponCard couponCard, int continuousIssueMonths) {
        return (couponCard.getContinuousIssueMonths() != 0 && couponCard.getContinuousIssueMonths() <= continuousIssueMonths);
    }
}
