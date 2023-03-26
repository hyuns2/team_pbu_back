package projectbuildup.mivv.domain.challenge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import projectbuildup.mivv.domain.challenge.dto.request.ChallengeRequestDto;
import projectbuildup.mivv.domain.member.entity.Member;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.time.LocalDate;
import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Challenge extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String mainTitle;
    @NonNull
    private String subTitle;
    @NonNull
    private LocalDate startDate;
    @NonNull
    private LocalDate endDate;
    @NonNull
    private List<String> content;
    @NonNull
    private int remittanceOnceLimit;
    @NonNull
    private int remittanceAvailableCount;
    @NonNull
    private String imageUrl;

    public void updateChallenge(ChallengeRequestDto.UpdateRequest updateRequestDto){
        this.mainTitle = updateRequestDto.getMainTitle();
        this.subTitle = updateRequestDto.getSubTitle();
        this.content = updateRequestDto.getContent();
        this.imageUrl = updateRequestDto.getImageUrl();
    }


}
