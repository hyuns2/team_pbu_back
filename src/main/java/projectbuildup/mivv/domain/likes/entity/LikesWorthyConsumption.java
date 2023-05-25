package projectbuildup.mivv.domain.likes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.global.common.BaseTimeEntity;

@Entity @AllArgsConstructor @NoArgsConstructor
@Getter
public class LikesWorthyConsumption extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn
    private User user;
    @ManyToOne @JoinColumn
    private WorthyConsumption worthyConsumption;
    @Enumerated(EnumType.STRING)
    private LikesCategory likesCategory = LikesCategory.WORTHY_CONSUMPTION;
    public LikesWorthyConsumption(User user, WorthyConsumption worthyConsumption){
        this.user = user;
        this.worthyConsumption = worthyConsumption;
    }

}
