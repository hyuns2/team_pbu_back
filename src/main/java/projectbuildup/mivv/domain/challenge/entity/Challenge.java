package projectbuildup.mivv.domain.challenge.entity;

import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.challenge.dto.request.ChallengeRequestDto;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private String mainTitle;
    private String subTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    @ElementCollection
    private List<String> description = new ArrayList<>();

    private int remittanceOnceLimit;
    private int remittanceAvailableCount;
    private String imageUrl;

    public void updateChallenge(ChallengeRequestDto.UpdateRequest updateRequestDto){
        this.mainTitle = updateRequestDto.getMainTitle();
        this.subTitle = updateRequestDto.getSubTitle();
        this.description = updateRequestDto.getContent();
        this.imageUrl = updateRequestDto.getImageUrl();
    }


}
