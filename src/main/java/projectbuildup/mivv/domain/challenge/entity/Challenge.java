package projectbuildup.mivv.domain.challenge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.lang.Nullable;
import projectbuildup.mivv.domain.challenge.dto.request.ChallengeRequestDto;
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
    //@NonNull
    @Nullable
    private LocalDate startDate;
    //@NonNull
    @Nullable
    private LocalDate endDate;
    @NonNull
    private List<String> content;
    //@NonNull
    @Nullable
    private int remittanceOnceLimit;
    //@NonNull
    @Nullable
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
