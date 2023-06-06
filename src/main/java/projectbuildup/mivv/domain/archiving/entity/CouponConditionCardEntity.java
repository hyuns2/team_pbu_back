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

    public void updateCard(ArchivingDto.updateCouponCardRequestDto dto, String imagePath) throws IOException {
        if (dto.getTitle() != null) {
            this.title = dto.getTitle();
        }
        if (dto.getSubTitle() != null) {
            this.subTitle = dto.getSubTitle();
        }
        if (dto.getSentences().size() > 0) {
            this.sentences = dto.getSentences().toString();
        }
        if (dto.getImage() != null) {
            this.imagePath = imagePath;
        }
        if (dto.getWhatNumber() != null) {
            this.whatNumber = dto.getWhatNumber();
        }
        if (dto.getHowSuccessive() != null) {
            this.howSuccessive = dto.getHowSuccessive();
        }
    }

}
