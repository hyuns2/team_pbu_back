package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@Entity
@DiscriminatorValue("CouponCondition")
@Table(name = "CouponConditionCard")
public class CouponConditionCardEntity extends CardEntity {

    // 몇번째 발급자인지
    protected Integer whatNumber;

    // 몇번 이상 연속 발급 성공자인지
    protected Integer howSuccessive;

    public void updateCard(ArchivingDto.updateCouponCardRequestDto dto, String imagePath) {
        if (dto.getKind() != null) {
            this.kind = dto.getKind();
        }
        if (dto.getTitle() != null) {
            this.title = dto.getTitle();
        }
        if (dto.getSubTitle() != null) {
            this.subTitle = dto.getSubTitle();
        }
        if (dto.getSentence() != null) {
            this.sentence = dto.getSentence();
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
