package projectbuildup.gasomann.domain.likes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.gasomann.domain.user.entity.User;
import projectbuildup.gasomann.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.gasomann.global.common.BaseTimeEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "likes_worthy_consumption")
public class LikesWorthyConsumption extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_likewc_to_user"))
    private User user;
    @ManyToOne
    @JoinColumn(name = "worthy_consumption_id", foreignKey = @ForeignKey(name = "fk_likewc_to_wc"))
    private WorthyConsumption worthyConsumption;
    @Enumerated(EnumType.STRING)
    @Column(name = "likes_category")
    private LikesCategory likesCategory = LikesCategory.WORTHY_CONSUMPTION;

    public LikesWorthyConsumption(User user, WorthyConsumption worthyConsumption) {
        this.user = user;
        this.worthyConsumption = worthyConsumption;
    }

}
