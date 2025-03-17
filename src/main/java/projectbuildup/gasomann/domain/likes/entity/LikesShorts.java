package projectbuildup.gasomann.domain.likes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.gasomann.domain.shorts.entity.Shorts;
import projectbuildup.gasomann.domain.user.entity.User;
import projectbuildup.gasomann.global.common.BaseTimeEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "likes_shorts")
public class LikesShorts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_likeshorts_to_user"))
    private User user;
    @ManyToOne
    @JoinColumn(name = "shorts_id", foreignKey = @ForeignKey(name = "fk_likeshorts_to_shorts"))
    private Shorts shorts;
    @Enumerated(EnumType.STRING)
    @Column(name = "likes_category")
    private LikesCategory likesCategory;

    public LikesShorts(User user, Shorts shorts, LikesCategory likesCategory) {
        this.user = user;
        this.shorts = shorts;
        this.likesCategory = likesCategory;
    }

}
