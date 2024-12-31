package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@DiscriminatorValue("general")
public class GeneralCard extends Card {
    public static GeneralCard of(String title, String subTitle, List<String> sentences, String imagePath) {
        return GeneralCard.builder()
                .type(CardType.GENERAL)
                .title(title)
                .subTitle(subTitle)
                .sentences(sentences)
                .imagePath(imagePath).build();
    }

    public void update(String title, String subTitle, List<String> sentences, String imagePath) {
        this.title = title;
        this.type = CardType.GENERAL;
        this.subTitle = subTitle;
        this.sentences = sentences;
        this.imagePath = imagePath;
    }
}
