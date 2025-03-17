package projectbuildup.gasomann.domain.archiving.entity;

import jakarta.persistence.*;
import lombok.*;
import projectbuildup.gasomann.domain.user.entity.User;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_card")
public class UserCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_usercard_to_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false, foreignKey = @ForeignKey(name = "fk_usercard_to_card"))
    private Card card;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "is_new", nullable = false)
    private Boolean isNew;

    @Builder
    public UserCard(User user, Card card) {
        this.user = user;
        this.card = card;
        this.date = LocalDate.now();
        this.isNew = true;
    }

    public static UserCard of(User user, Card card) {
        return UserCard.builder()
                .user(user)
                .card(card).build();
    }
}
