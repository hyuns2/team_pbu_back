package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@DiscriminatorColumn(name = "dType")
@Table(name="Card")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false, length = 30)
    protected String kind;

    @Column(nullable = false, length = 30)
    protected String title;

    @Column(nullable = false, length = 30)
    protected String subTitle;

    @Column(nullable = false, length = 30)
    protected String sentence;

    @Column(nullable = false, length = 5000)
    protected String imagePath;

    @OneToMany(mappedBy = "cardEntity", cascade = CascadeType.ALL)
    private List<UserCardEntity> userCards = new ArrayList<>();

    public void updateCard(ArchivingDto.updateGeneralCardRequestDto dto, String imagePath) throws IOException {
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
            this.imagePath = imagePath;
        }
    }

}
