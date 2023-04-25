package projectbuildup.mivv.domain.challenge.entity;


import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.challenge.dto.ChallengeDto;
import projectbuildup.mivv.domain.image.Image;
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
    private Image image;

    private LocalDate startDate;
    private LocalDate endDate;

    public void update(ChallengeDto.UpdateRequest requestDto) {
        this.mainTitle = requestDto.getMainTitle();
        this.subTitle = requestDto.getSubTitle();
        this.description = requestDto.getDescription();
        this.remittanceOnceLimit = requestDto.getRemittanceOnceLimit();
        this.remittanceAvailableCount = requestDto.getRemittanceAvailableCount();
    }

    public void updateImage(Image image) {
        this.image = image;
    }

    public static Challenge from(ChallengeDto.CreationRequest requestDto, Image image) {
        return Challenge.builder()
                .mainTitle(requestDto.getMainTitle())
                .subTitle(requestDto.getSubTitle())
                .description(requestDto.getDescription())
                .remittanceOnceLimit(requestDto.getRemittanceOnceLimit())
                .remittanceAvailableCount(requestDto.getRemittanceAvailableCount())
                .image(image)
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .build();
    }

}
