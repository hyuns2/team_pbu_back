package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;

import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@DiscriminatorValue("saving")
public class SavingCard extends Card {
    @Column(name = "charge", nullable = false)
    protected Integer charge;

    @Column(name = "count", nullable = false)
    protected Integer count;

    @Column(name = "term", nullable = false)
    protected Integer term;

    public void updateCard(ArchivingDto.createOrUpdateSavingCardRequestDto dto, String imagePath) {
        this.type = CardType.SAVING;
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

    public boolean equals(SavingCard savingCardEntity) {
        return this.id.equals(savingCardEntity.getId()) &&
                this.type.equals(savingCardEntity.getType()) &&
                this.title.equals(savingCardEntity.getTitle()) &&
                this.subTitle.equals(savingCardEntity.getSubTitle()) &&
                this.sentences.equals(savingCardEntity.getSentences()) &&
                this.imagePath.equals(savingCardEntity.getImagePath()) &&
                this.charge.equals(savingCardEntity.getCharge()) &&
                this.count.equals(savingCardEntity.getCount()) &&
                this.term.equals(savingCardEntity.getTerm());
    }

}
