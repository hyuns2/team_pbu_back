package projectbuildup.mivv.domain.challenge.dto.response;

import projectbuildup.mivv.domain.challenge.entity.Challenge;

import java.util.List;

public class ChallengeResponseDto {

    public static class ReadResponse {
        String mainTitle;
        String subTitle;

        List<String> content;

        String imageUrl;

        public ReadResponse(Challenge challenge){
            this.mainTitle = challenge.getMainTitle();
            this.subTitle = challenge.getSubTitle();
            this.content = challenge.getContent();
            this.imageUrl = challenge.getImageUrl();
        }
    }
}
