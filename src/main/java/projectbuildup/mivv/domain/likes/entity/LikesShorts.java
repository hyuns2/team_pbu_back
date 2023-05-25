package projectbuildup.mivv.domain.likes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.mivv.domain.shorts.entity.Shorts;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.BaseTimeEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LikesShorts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn
    private User user;
    @ManyToOne
    @JoinColumn
    private Shorts shorts;
    @Enumerated(EnumType.STRING)
    private LikesCategory likesCategory;

    public LikesShorts(User user, Shorts shorts, LikesCategory likesCategory) {
        this.user = user;
        this.shorts = shorts;
        this.likesCategory = likesCategory;
    }

}
