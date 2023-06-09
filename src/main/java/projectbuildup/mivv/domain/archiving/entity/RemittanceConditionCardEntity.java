package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@Entity
@DiscriminatorValue("RemittanceCondition")
@Table(name = "remittance_card")
public class RemittanceConditionCardEntity extends CardEntity {

    @Column(name = "charge", nullable = false)
    protected Integer charge;

    @Column(name = "count", nullable = false)
    protected Integer count;

    @Column(name = "term", nullable = false)
    protected Integer term;

    public void updateCard(ArchivingDto.createOrUpdateRemittanceCardRequestDto dto, String imagePath) {
        this.type = CardType.REMITTANCE;
        this.title = dto.getTitle();
        this.subTitle = dto.getSubTitle();
        this.sentences = dto.getSentences().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        this.imagePath = imagePath;
        this.charge = dto.getCharge();
        this.count = dto.getCount();
        this.term = dto.getTerm();
    }

    public boolean equals(RemittanceConditionCardEntity remittanceConditionCardEntity) {
        return this.id.equals(remittanceConditionCardEntity.getId()) &&
                this.type.equals(remittanceConditionCardEntity.getType()) &&
                this.title.equals(remittanceConditionCardEntity.getTitle()) &&
                this.subTitle.equals(remittanceConditionCardEntity.getSubTitle()) &&
                this.sentences.equals(remittanceConditionCardEntity.getSentences()) &&
                this.imagePath.equals(remittanceConditionCardEntity.getImagePath()) &&
                this.charge.equals(remittanceConditionCardEntity.getCharge()) &&
                this.count.equals(remittanceConditionCardEntity.getCount()) &&
                this.term.equals(remittanceConditionCardEntity.getTerm());
    }

}
