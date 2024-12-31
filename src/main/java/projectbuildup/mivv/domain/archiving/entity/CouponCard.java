package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@DiscriminatorValue("coupon")
public class CouponCard extends Card {

    @Column(name = "what_number")
    protected Integer whatNumber;

    @Column(name = "how_successive")
    protected Integer howSuccessive;

    public static CouponCard of(String title, String subTitle, List<String> sentences, String imagePath, Integer whatNumber, Integer howSuccessive) {
        return CouponCard.builder()
                .type(CardType.COUPON)
                .title(title)
                .subTitle(subTitle)
                .sentences(sentences)
                .imagePath(imagePath)
                .whatNumber(whatNumber)
                .howSuccessive(howSuccessive).build();
    }

    public void update(String title, String subTitle, List<String> sentences, String imagePath, Integer whatNumber, Integer howSuccessive) {
        this.title = title;
        this.type = CardType.COUPON;
        this.subTitle = subTitle;
        this.sentences = sentences;
        this.imagePath = imagePath;
        this.whatNumber = whatNumber;
        this.howSuccessive = howSuccessive;
    }
}
