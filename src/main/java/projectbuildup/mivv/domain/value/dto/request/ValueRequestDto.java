package projectbuildup.mivv.domain.value.dto.request;

import lombok.*;
import projectbuildup.mivv.domain.value.entity.Value;

import java.util.List;

public class ValueRequestDto {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class CreationRequest {
        String title;
        List<String> hashtags;
        int maxParticipants;
        String videoUrl;
        String imageUrl;
        String detailImageUrl;
        String detailBackgroundImageUrl;
        int originalPrice;
        int salePrice;
        List<String> whyRecommendation;
        String priceTag;
        String placeImageUrl;
        String placeTag;
        List<String> summary;

        public Value toEntity(){
            return Value.builder()
                    .title(title)
                    .hashtags(hashtags)
                    .maxParticipants(maxParticipants)
                    .videoUrl(videoUrl)
                    .imageUrl(imageUrl)
                    .detailImageUrl(detailImageUrl)
                    .detailBackgroundImageUrl(detailBackgroundImageUrl)
                    .originalPrice(originalPrice)
                    .salePrice(salePrice)
                    .whyRecommendation(whyRecommendation)
                    .priceTag(priceTag)
                    .placeImageUrl(placeImageUrl)
                    .placeTag(placeTag)
                    .summary(summary)
                    .build();
        }
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class ReadSummaryRequest {
        Long id;
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class ReadDetailRequest {
        Long id;
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class UpdateContentRequest{
        Long id;
        String title;
        List<String> hashtags;
        int maxParticipants;
        List<String> whyRecommendation;
        List<String> summary;
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class UpdateUrlRequest{
        Long id;
        String videoUrl;
        String imageUrl;
        String detailImageUrl;
        String detailBackgroundImageUrl;
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class UpdatePriceRequest{
        Long id;
        int originalPrice;
        int salePrice;
        private String priceTag;

    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class UpdatePlaceRequest{
        Long id;
        String placeImageUrl;
        String placeTag;

    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class DeleteRequest{
        Long id;

    }


}
