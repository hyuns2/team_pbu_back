package projectbuildup.mivv.domain.worthyConsumption.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumptionUrl;

import java.util.List;
@Schema(description = "가치소비 요청 DTO")
public class WorthyConsumptionRequestDto {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "가치소비 생성 요청 DTO")
    public static class CreationRequest {
        String title;
        List<String> hashtags;
        int maxParticipants;
        int originalPrice;
        int salePrice;
        List<String> whyRecommendation;
        String priceTag;
        String placeTag;
        List<String> summary;
        WorthyConsumptionUrlRequestDto.CreationRequest worthyConsumptionUrlRequest;

        /*public WorthyConsumption toEntity(){
            return WorthyConsumption.builder()
                    .title(title)
                    .hashtags(hashtags)
                    .maxParticipants(maxParticipants)
                    .originalPrice(originalPrice)
                    .salePrice(salePrice)
                    .whyRecommendation(whyRecommendation)
                    .priceTag(priceTag)
                    .placeTag(placeTag)
                    .summary(summary)
                    .build();
        }*/
        public WorthyConsumption toEntity(WorthyConsumptionUrl worthyConsumptionUrl){
            return WorthyConsumption.builder()
                    .title(title)
                    .hashtags(hashtags)
                    .maxParticipants(maxParticipants)
                    .worthyConsumptionUrl(worthyConsumptionUrl)
                    .originalPrice(originalPrice)
                    .salePrice(salePrice)
                    .whyRecommendation(whyRecommendation)
                    .priceTag(priceTag)
                    .placeTag(placeTag)
                    .summary(summary)
                    .build();
        }
    }
    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    public static class IdRequest {
        Long id;
    }
    @AllArgsConstructor
    //@NoArgsConstructor
    @Getter
    @Setter
    public static class ReadBasicRequest extends WorthyConsumptionRequestDto.IdRequest{

    }
    @AllArgsConstructor
    //@NoArgsConstructor
    @Getter
    @Setter
    public static class ReadSummaryRequest extends WorthyConsumptionRequestDto.IdRequest{

    }
    @AllArgsConstructor
    //@NoArgsConstructor
    @Getter
    @Setter
    public static class ReadDetailRequest extends WorthyConsumptionRequestDto.IdRequest{

    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public class UpdateContentRequest extends WorthyConsumptionRequestDto.IdRequest{
        String title;
        List<String> hashtags;
        int maxParticipants;
        List<String> whyRecommendation;
        List<String> summary;
    }
    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    public class UpdateUrlRequest extends WorthyConsumptionRequestDto.IdRequest {
        String videoUrl;
        String imageUrl;
        String detailImageUrl;
        String detailBackgroundImageUrl;
        String placeImageUrl;
    }
    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    public class UpdatePriceRequest extends WorthyConsumptionRequestDto.IdRequest{
        int originalPrice;
        int salePrice;
        String priceTag;

    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public class UpdatePlaceRequest extends WorthyConsumptionRequestDto.IdRequest{
        String placeTag;

    }
    //@NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class DeleteRequest extends WorthyConsumptionRequestDto.IdRequest{

    }


}
