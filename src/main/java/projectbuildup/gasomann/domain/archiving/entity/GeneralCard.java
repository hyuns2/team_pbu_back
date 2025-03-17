package projectbuildup.gasomann.domain.archiving.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@DiscriminatorValue("general")
public class GeneralCard extends Card {
    public static GeneralCard of(String title, String subTitle, List<String> sentences, String imagePath) {
        return GeneralCard.builder()
                .cardType(CardType.GENERAL)
                .title(title)
                .subTitle(subTitle)
                .sentences(sentences)
                .imagePath(imagePath).build();
    }

    public void update(String title, String subTitle, List<String> sentences, String imagePath) {
        if (title != null)
            this.title = title;
        if (subTitle != null)
            this.subTitle = subTitle;
        if (sentences != null)
            this.sentences = sentences;
        if (imagePath != null)
            this.imagePath = imagePath;
    }
}
