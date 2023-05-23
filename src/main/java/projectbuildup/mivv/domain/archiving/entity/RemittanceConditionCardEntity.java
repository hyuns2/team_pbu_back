package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@Entity
@DiscriminatorValue("RemittanceCondition")
@Table(name = "RemittanceConditionCard")
public class RemittanceConditionCardEntity extends CardEntity {

    protected Integer charge;
    protected Integer count;
    protected Integer term;

    public void updateCard(ArchivingDto.updateNumericalCardRequestDto dto, String imagePath) {
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
        if (dto.getCharge() != null) {
            this.charge = dto.getCharge();
        }
        if (dto.getCount() != null) {
            this.count = dto.getCount();
        }
        if (dto.getTerm() != null) {
            this.term = dto.getTerm();
        }
    }

    public boolean equals(RemittanceConditionCardEntity remittanceConditionCardEntity) {
        return this.id.equals(remittanceConditionCardEntity.getId()) &&
                this.kind.equals(remittanceConditionCardEntity.getKind()) &&
                this.title.equals(remittanceConditionCardEntity.getTitle()) &&
                this.subTitle.equals(remittanceConditionCardEntity.getSubTitle()) &&
                this.sentence.equals(remittanceConditionCardEntity.getSentence()) &&
                this.imagePath.equals(remittanceConditionCardEntity.getImagePath()) &&
                this.charge.equals(remittanceConditionCardEntity.getCharge()) &&
                this.count.equals(remittanceConditionCardEntity.getCount()) &&
                this.term.equals(remittanceConditionCardEntity.getTerm());
    }

}
