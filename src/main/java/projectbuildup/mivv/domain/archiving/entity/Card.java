package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import projectbuildup.mivv.domain.archiving.entity.converter.ListToStringConverter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "d_type")
public abstract class Card {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "type", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    protected CardType type;

    @Column(name = "title", nullable = false, length = 30)
    protected String title;

    @Column(name = "sub_title", nullable = false, length = 30)
    protected String subTitle;

    @Convert(converter = ListToStringConverter.class)
    @Column(name = "sentences", nullable = false, length = 300)
    protected List<String> sentences = new ArrayList<>();

    @Column(name = "image_path", nullable = false, length = 5000)
    protected String imagePath;
}
