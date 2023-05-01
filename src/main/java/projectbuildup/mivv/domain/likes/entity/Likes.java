package projectbuildup.mivv.domain.likes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import projectbuildup.mivv.domain.shorts.entity.Shorts;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Likes extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @ElementCollection
    private List<Long> userLikesWorthyConsumptionList = new ArrayList<>();
    @Getter
    @ElementCollection
    private List<Long> userLikesSavingShortsList = new ArrayList<>();
    @Getter
    @ElementCollection
    private List<Long> userLikesEduShortsList = new ArrayList<>();

}
