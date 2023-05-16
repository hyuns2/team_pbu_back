package projectbuildup.mivv.domain.challenge.entity;


import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.challenge.dto.ChallengeDto;
import projectbuildup.mivv.global.common.imageStore.Image;
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
    private long maxSavingAmount;
    private long minSavingAmount;
    private long limitedNumberOfTimes;
    private Image image;

    private LocalDate startDate;
    private LocalDate endDate;

    public void update(ChallengeDto.UpdateRequest requestDto) {
        this.mainTitle = requestDto.getMainTitle();
        this.subTitle = requestDto.getSubTitle();
        this.description = requestDto.getDescription();
        this.maxSavingAmount = requestDto.getRemittanceOnceLimit();
        this.limitedNumberOfTimes = requestDto.getRemittanceAvailableCount();
    }

    public void updateImage(Image image) {
        this.image = image;
    }

    public static Challenge from(ChallengeDto.CreationRequest requestDto, Image image) {
        return Challenge.builder()
                .mainTitle(requestDto.getMainTitle())
                .subTitle(requestDto.getSubTitle())
                .description(requestDto.getDescription())
                .maxSavingAmount(requestDto.getMaxSavingAmount())
                .minSavingAmount(requestDto.getMinSavingAmount())
                .limitedNumberOfTimes(requestDto.getLimitedNumberOfTimes())
                .image(image)
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .build();
    }

}
