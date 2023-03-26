package projectbuildup.mivv.domain.challenge.dto.response;

import lombok.NonNull;
import projectbuildup.mivv.domain.challenge.entity.Challenge;

import java.time.LocalDate;
import java.util.List;

public class ChallengeResponseDto {

    public static class ReadSummaryResponse {
        String mainTitle;
        String subTitle;

        List<String> content;

        String imageUrl;

        public void readSummaryResponse(Challenge challenge){
            this.mainTitle = challenge.getMainTitle();
            this.subTitle = challenge.getSubTitle();
            this.content = challenge.getContent();
            this.imageUrl = challenge.getImageUrl();
        }
    }
    public static class ReadSpecificResponse {
        String mainTitle;
        String subTitle;

        List<String> content;

        String imageUrl;

        LocalDate startDate;
        LocalDate endDate;

        int remittanceOnceLimit;
        int remittanceAvailableCount;

        public void readSpecificResponse(Challenge challenge){
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
