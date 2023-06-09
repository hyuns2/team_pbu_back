package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@DiscriminatorColumn(name = "d_type")
@DiscriminatorValue("GeneralCondition")
@Table(name="card")
public class CardEntity {

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

    @Column(name = "sentences", nullable = false, length = 300)
    protected String sentences;

    @Column(name = "image_path", nullable = false, length = 5000)
    protected String imagePath;

    @OneToMany(mappedBy = "cardEntity", cascade = CascadeType.ALL)
    private List<UserCardEntity> userCards = new ArrayList<>();

    public void updateCard(ArchivingDto.createOrUpdateGeneralCardRequestDto dto, String imagePath) {
        this.type = CardType.GENERAL;
        this.title = dto.getTitle();
        this.subTitle = dto.getSubTitle();
        this.sentences = dto.getSentences().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        this.imagePath = imagePath;
    }

}
