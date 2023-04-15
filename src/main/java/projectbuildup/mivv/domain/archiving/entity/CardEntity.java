package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    protected String image;

    @OneToMany(mappedBy = "cardEntity", cascade = CascadeType.ALL)
    private List<UserCardEntity> userCards = new ArrayList<>();

    public CardEntity(String kind, String title, String subTitle, String sentence, String image) {
        this.kind = kind;
        this.title = title;
        this.subTitle = subTitle;
        this.sentence = sentence;
        this.image = image;
    }

}
