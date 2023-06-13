package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "user_card")
public class UserCardEntity {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_usercard_to_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", foreignKey = @ForeignKey(name = "fk_usercard_to_card"))
    private CardEntity cardEntity;

    @Column(name = "date", nullable = false)
    @Temporal(value = TemporalType.DATE)
    private LocalDate date;

    @Column(name = "is_new", nullable = false)
    private boolean isNew;

    public UserCardEntity(User user, CardEntity cardEntity, LocalDate date) {
        this.user = user;
        this.cardEntity = cardEntity;
        this.date = date;
        this.isNew = true;
    }

    public void updateIsNew() {
        this.isNew = false;
    }

}
