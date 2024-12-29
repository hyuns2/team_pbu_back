package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;

import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@DiscriminatorValue("coupon")
public class CouponCard extends Card {

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
