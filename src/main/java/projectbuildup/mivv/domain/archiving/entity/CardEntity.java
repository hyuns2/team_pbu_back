package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(name = "dType")
@Table(name="Card")
public abstract class CardEntity {

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
    protected String image;

    public CardEntity(String kind, String title, String subTitle, String sentence, String image) {
        this.kind = kind;
        this.title = title;
        this.subTitle = subTitle;
        this.sentence = sentence;
        this.image = image;
    }

}
