package projectbuildup.mivv.domain.challenge.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.mivv.domain.challenge.entity.Challenge;

import java.time.LocalDate;
import java.util.List;

public class ChallengeResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadSummaryResponse {
        String mainTitle;
        String subTitle;

        List<String> content;

        String imageUrl;

        public ReadSummaryResponse(Challenge challenge){
            this.mainTitle = challenge.getMainTitle();
            this.subTitle = challenge.getSubTitle();
            this.content = challenge.getContent();
            this.imageUrl = challenge.getImageUrl();
        }
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadSpecificResponse {
        String mainTitle;
        String subTitle;

        List<String> content;

        String imageUrl;

        LocalDate startDate;
        LocalDate endDate;

        int remittanceOnceLimit;
        int remittanceAvailableCount;

        public ReadSpecificResponse(Challenge challenge){
            this.mainTitle = challenge.getMainTitle();
            this.subTitle = challenge.getSubTitle();
            this.content = challenge.getContent();
            this.imageUrl = challenge.getImageUrl();
            this.startDate = challenge.getStartDate();
            this.endDate = challenge.getEndDate();
            this.remittanceOnceLimit = challenge.getRemittanceOnceLimit();
            this.remittanceAvailableCount = challenge.getRemittanceAvailableCount();
        }
    }
}
