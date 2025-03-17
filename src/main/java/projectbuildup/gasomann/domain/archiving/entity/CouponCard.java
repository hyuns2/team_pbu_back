package projectbuildup.gasomann.domain.archiving.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@DiscriminatorValue("coupon")
public class CouponCard extends Card {
    @Column(name = "coupon_issue_order")
    private Integer couponIssueOrder;

    @Column(name = "continuous_issue_months")
    private Integer continuousIssueMonths;

    public static CouponCard of(String title, String subTitle, List<String> sentences, String imagePath, Integer couponIssueOrder, Integer continuousIssueMonths) {
        return CouponCard.builder()
                .cardType(CardType.COUPON)
                .title(title)
                .subTitle(subTitle)
                .sentences(sentences)
                .imagePath(imagePath)
                .couponIssueOrder(couponIssueOrder)
                .continuousIssueMonths(continuousIssueMonths).build();
    }

    public void update(String title, String subTitle, List<String> sentences, String imagePath, Integer whatNumber, Integer howSuccessive) {
        if (title != null)
            this.title = title;
        if (subTitle != null)
            this.subTitle = subTitle;
        if (sentences != null)
            this.sentences = sentences;
        if (imagePath != null)
            this.imagePath = imagePath;
        if (couponIssueOrder != null)
            this.couponIssueOrder = whatNumber;
        if (continuousIssueMonths != null)
            this.continuousIssueMonths = howSuccessive;
    }
}
