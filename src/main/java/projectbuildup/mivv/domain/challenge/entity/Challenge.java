package projectbuildup.mivv.domain.challenge.entity;


import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.challenge.dto.ChallengeDto;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class Challenge extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String mainTitle;
    private String subTitle;
    @ElementCollection
    private List<String> description = new ArrayList<>();
    private long remittanceOnceLimit;
    private long remittanceAvailableCount;
    private String imageUrl;

    private LocalDate startDate;
    private LocalDate endDate;

    public void update(ChallengeDto.UpdateRequest requestDto) {
        this.mainTitle = requestDto.getMainTitle();
        this.subTitle = requestDto.getSubTitle();
        this.description = requestDto.getDescription();
        this.remittanceOnceLimit = requestDto.getRemittanceOnceLimit();
        this.remittanceAvailableCount = requestDto.getRemittanceAvailableCount();
        this.imageUrl = requestDto.getImageUrl();
    }

    public static Challenge of(ChallengeDto.CreationRequest requestDto) {
        return Challenge.builder()
                .mainTitle(requestDto.getMainTitle())
                .subTitle(requestDto.getSubTitle())
                .description(requestDto.getDescription())
                .remittanceOnceLimit(requestDto.getRemittanceOnceLimit())
                .remittanceAvailableCount(requestDto.getRemittanceAvailableCount())
                .imageUrl(requestDto.getImageUrl())
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .build();
    }

}
