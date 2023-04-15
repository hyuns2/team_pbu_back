package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "UserCard")
public class UserCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cardId")
    private CardEntity cardEntity;

    @Column(nullable = false)
    @Temporal(value = TemporalType.DATE)
    private LocalDate date;

    public UserCardEntity(User user, CardEntity cardEntity, LocalDate date) {
        this.user = user;
        this.cardEntity = cardEntity;
        this.date = date;
    }

}