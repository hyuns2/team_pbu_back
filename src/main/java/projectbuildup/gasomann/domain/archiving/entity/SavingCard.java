package projectbuildup.gasomann.domain.archiving.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@DiscriminatorValue("saving")
public class SavingCard extends Card {
    @Column(name = "charge")
    private Integer charge;

    @Column(name = "count")
    private Integer count;

    @Column(name = "term")
    private Integer term;

    public static SavingCard of(String title, String subTitle, List<String> sentences, String imagePath, Integer charge, Integer count, Integer term) {
        return SavingCard.builder()
                .title(title)
                .cardType(CardType.SAVING)
                .subTitle(subTitle)
                .sentences(sentences)
                .imagePath(imagePath)
                .charge(charge)
                .count(count)
                .term(term).build();
    }

    public void update(String title, String subTitle, List<String> sentences, String imagePath, Integer charge, Integer count, Integer term) {
        if (title != null)
            this.title = title;
        if (subTitle != null)
            this.subTitle = subTitle;
        if (sentences != null)
            this.sentences = sentences;
        if (imagePath != null)
            this.imagePath = imagePath;
        if (charge != null)
            this.charge = charge;
        if (count != null)
            this.count = count;
        if (term != null)
            this.term = term;
    }
}
