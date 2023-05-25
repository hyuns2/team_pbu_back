package projectbuildup.mivv.domain.challenge.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Columns;
import projectbuildup.mivv.domain.challenge.dto.ChallengeDto;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.global.common.BaseTimeEntity;
import projectbuildup.mivv.global.common.imageStore.Image;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@Table(name = "challenge")
public class Challenge extends BaseTimeEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "main_title")
    private String mainTitle;
    @Column(name = "sub_title")
    private String subTitle;
    @CollectionTable(
            name = "challenge_description",
            joinColumns = @JoinColumn(name = "challenge_id", referencedColumnName = "id")
    )
    @Column(name = "description")
    @ElementCollection
    private List<String> description = new ArrayList<>();
    @Column(name = "max_saving_amount")
    private long maxSavingAmount;
    @Column(name = "min_saving_amount")
    private long minSavingAmount;
    @Column(name = "limited_number_of_times")
    private long limitedNumberOfTimes;
    @Columns(columns = {
            @Column(name = "original_image_name"),
            @Column(name = "image_path"),
            @Column(name = "store_image_name")
    })
    private Image image;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "challenge", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Participation> participationList;

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

    public static Challenge of(ChallengeDto.CreationRequest requestDto, Image image) {
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

    public boolean canRemit(Long amount) {
        return this.minSavingAmount <= amount && amount <= this.maxSavingAmount;
    }
}
