package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DiscriminatorValue("NumericalCondition")
@Table(name = "NumericalConditionCard")
public class NumericalConditionCardEntity extends CardEntity {

    protected Integer charge;
    protected Integer count;
    protected Integer term;

    @Builder
    public NumericalConditionCardEntity(String kind, String title, String subTitle, String sentence, String image, int charge, int count, int term) {
        super(kind, title, subTitle, sentence, image);
        this.charge = charge;
        this.count = count;
        this.term = term;
    }

    public void updateCard(ArchivingDto.updateNumericalConditionCardRequestDto dto) {
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
            this.image = dto.getImage();
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

}
