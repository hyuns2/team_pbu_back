package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@Entity
@DiscriminatorValue("RemittanceCondition")
@Table(name = "remittance_card")
public class RemittanceConditionCardEntity extends CardEntity {

    protected Integer charge;
    protected Integer count;
    protected Integer term;

    public void updateCard(ArchivingDto.updateRemittanceCardRequestDto dto, String imagePath) {
        if (dto.getTitle() != null) {
            this.title = dto.getTitle();
        }
        if (dto.getSubTitle() != null) {
            this.sub_title = dto.getSubTitle();
        }
        if (dto.getSentence() != null) {
            this.sentence = dto.getSentence();
        }
        if (dto.getImage() != null) {
            this.image_path = imagePath;
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
                this.type.equals(remittanceConditionCardEntity.getType()) &&
                this.title.equals(remittanceConditionCardEntity.getTitle()) &&
                this.sub_title.equals(remittanceConditionCardEntity.getSub_title()) &&
                this.sentence.equals(remittanceConditionCardEntity.getSentence()) &&
                this.image_path.equals(remittanceConditionCardEntity.getImage_path()) &&
                this.charge.equals(remittanceConditionCardEntity.getCharge()) &&
                this.count.equals(remittanceConditionCardEntity.getCount()) &&
                this.term.equals(remittanceConditionCardEntity.getTerm());
    }

}