package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@Entity
@DiscriminatorValue("CouponCondition")
@Table(name = "coupon_card")
public class CouponConditionCardEntity extends CardEntity {

    @Column(name = "what_number", nullable = false)
    protected Integer whatNumber;

    @Column(name = "how_successive", nullable = false)
    protected Integer howSuccessive;

    public void updateCard(ArchivingDto.createOrUpdateCouponCardRequestDto dto, String imagePath) {
        this.type = CardType.COUPON;
        this.title = dto.getTitle();
        this.subTitle = dto.getSubTitle();
        this.sentences = dto.getSentences().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        this.imagePath = imagePath;
        this.whatNumber = dto.getWhatNumber();
        this.howSuccessive = dto.getHowSuccessive();
    }

}
