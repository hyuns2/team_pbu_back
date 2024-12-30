package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@DiscriminatorValue("saving")
public class SavingCard extends Card {
    @Column(name = "charge")
    protected Integer charge;

    @Column(name = "count")
    protected Integer count;

    @Column(name = "term")
    protected Integer term;

    public static SavingCard of(String title, String subTitle, List<String> sentences, String imagePath, Integer charge, Integer count, Integer term) {
        return SavingCard.builder()
                .title(title)
                .type(CardType.SAVING)
                .subTitle(subTitle)
                .sentences(sentences.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", ")))
                .imagePath(imagePath)
                .charge(charge)
                .count(count)
                .term(term).build();
    }

    public void update(String title, String subTitle, List<String> sentences, String imagePath, Integer charge, Integer count, Integer term) {
        this.title = title;
        this.type = CardType.SAVING;
        this.subTitle = subTitle;
        this.sentences = sentences.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(", "));
        this.imagePath = imagePath;
        this.charge = charge;
        this.count = count;
        this.term = term;
    }
}
