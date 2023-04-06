package projectbuildup.mivv.domain.worthyConsumption.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;

import java.util.List;
@Schema(description = "가치소비 응답 DTO")
public class WorthyConsumptionResponseDto {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ReadSummaryResponse {
        private String title;
        private int originalPrice;
        private int salePrice;
        private String priceTag;
        private List<String> summary;

        public ReadSummaryResponse(WorthyConsumption worthyConsumption) {
            this.title = worthyConsumption.getTitle();
            this.originalPrice = worthyConsumption.getOriginalPrice();
            this.salePrice = worthyConsumption.getSalePrice();
            this.priceTag = worthyConsumption.getPriceTag();
            this.summary = worthyConsumption.getSummary();
        }
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ReadBasicResponse extends ReadSummaryResponse{
        private List<String> hashtags;
        private String videoUrl;
        private String imageUrl;

        public ReadBasicResponse(WorthyConsumption worthyConsumption) {
            this.hashtags = worthyConsumption.getHashtags();
            this.videoUrl = worthyConsumption.getWorthyConsumptionUrl().getVideoUrl();
            this.imageUrl = worthyConsumption.getWorthyConsumptionUrl().getImageUrl();
        }

    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ReadDetailResponse extends ReadBasicResponse{
        private int maxParticipants;
        private String detailImageUrl;
        private String detailBackgroundImageUrl;
        private List<String> whyRecommendation;
        private String placeImageUrl;
        private String placeTag;

        public ReadDetailResponse(WorthyConsumption worthyConsumption) {
            this.maxParticipants = worthyConsumption.getMaxParticipants();
            this.detailImageUrl = worthyConsumption.getWorthyConsumptionUrl().getDetailImageUrl();
            this.detailBackgroundImageUrl = worthyConsumption.getWorthyConsumptionUrl().getDetailBackgroundImageUrl();
            this.whyRecommendation = worthyConsumption.getWhyRecommendation();
            this.placeImageUrl = worthyConsumption.getWorthyConsumptionUrl().getPlaceImageUrl();
            this.placeTag = worthyConsumption.getPlaceTag();
        }

    }
}