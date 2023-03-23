package projectbuildup.mivv.domain.challenge.dto.request;

import lombok.*;
import projectbuildup.mivv.domain.challenge.entity.Challenge;

import java.util.List;

public class ChallengeRequestDto {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreationRequest{
        String mainTitle;
        String subTitle;
        List<String> Content;
        String imageUrl;


        public Challenge toEntity() {
            return Challenge.builder()
                    .mainTitle(mainTitle)
                    .subTitle(subTitle)
                    .Content(Content)
                    .imageUrl(imageUrl)
                    .build();
        }

    }
    public static class UpdateRequest {

        int id;
        String mainTitle;
        String subTitle;
        List<String> content;
        String imageUrl;

    }

    public static class ReadRequest {
        int id;

    }
    public static class DeleteRequest{
        int id;

    }
}
