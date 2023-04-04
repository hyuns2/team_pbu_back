package projectbuildup.mivv.domain.value.dto.request;

import lombok.*;
import projectbuildup.mivv.domain.value.entity.Value;
import projectbuildup.mivv.domain.value.entity.ValueUrl;

import java.util.List;
@Data
public class ValueRequestDto {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class CreationRequest {
        String title;
        List<String> hashtags;
        int maxParticipants;
        ValueUrl valueUrl;

        int originalPrice;
        int salePrice;
        List<String> whyRecommendation;
        String priceTag;
        String placeTag;
        List<String> summary;

        public Value toEntity(){
            return Value.builder()
                    .title(title)
                    .hashtags(hashtags)
                    .maxParticipants(maxParticipants)
                    .valueUrl(valueUrl)
                    .originalPrice(originalPrice)
                    .salePrice(salePrice)
                    .whyRecommendation(whyRecommendation)
                    .priceTag(priceTag)
                    .placeTag(placeTag)
                    .summary(summary)
                    .build();
        }
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IdRequest {
        Long id;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReadSummaryRequest extends ValueRequestDto.IdRequest{

    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReadDetailRequest extends ValueRequestDto.IdRequest{
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class UpdateContentRequest extends ValueRequestDto.IdRequest{
        String title;
        List<String> hashtags;
        int maxParticipants;
        List<String> whyRecommendation;
        List<String> summary;
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class UpdateUrlRequest extends ValueRequestDto.IdRequest {
        String videoUrl;
        String imageUrl;
        String detailImageUrl;
        String detailBackgroundImageUrl;
        String placeImageUrl;
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class UpdatePriceRequest extends ValueRequestDto.IdRequest{
        int originalPrice;
        int salePrice;
        private String priceTag;

    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class UpdatePlaceRequest extends ValueRequestDto.IdRequest{
        String placeTag;

    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteRequest extends ValueRequestDto.IdRequest{

    }


}
