package projectbuildup.mivv.domain.worthyConsumption.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;

import java.util.List;
@Schema(description = "가치소비 응답 DTO")
public class WorthyConsumptionResponseDto {
    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter
    public static class ReadSummaryResponse {
        private String title;
        private int originalPrice;
        private int salePrice;
        private String priceTag;
        private int lastMonthAmount;
        private int maxParticipants;
        private String summary;

        public ReadSummaryResponse(WorthyConsumption worthyConsumption) {
            this.title = worthyConsumption.getTitle();
            this.originalPrice = worthyConsumption.getOriginalPrice();
            this.salePrice = worthyConsumption.getSalePrice();
            this.priceTag = worthyConsumption.getPriceTag();
            this.lastMonthAmount = worthyConsumption.getCondition().getLastMonthAmount();
            this.maxParticipants = worthyConsumption.getCondition().getMaxParticipants();
            this.summary = worthyConsumption.getSummary();
        }
    }
    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter
    public static class ReadBasicResponse extends ReadSummaryResponse{
        private List<String> hashtags;
        private String videoUrl;
        private String imageUrl;
        private Boolean isIssuable;//이거 처리 해야 됨
        private Boolean isLiked;//이거 처리 해야 됨

        public ReadBasicResponse(WorthyConsumption worthyConsumption) {
            super(worthyConsumption);
            this.hashtags = worthyConsumption.getHashtags();
            this.videoUrl = worthyConsumption.getWorthyConsumptionUrl().getVideoUrl();
            this.imageUrl = worthyConsumption.getWorthyConsumptionUrl().getImageUrl();
        }

    }
    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter
    @Builder
    public static class ReadDetailResponse{
        private String title;
        private List<String> hashtags;
        private String detailImageUrl;
        private String detailBackgroundImageUrl;
        private String videoUrl;
        private List<String> whyRecommendation;
        private int originalPrice;
        private int salePrice;
        private String priceTag;
        private String placeImageUrl;
        private String placeTag;

        public ReadDetailResponse(WorthyConsumption worthyConsumption) {
            this.title = worthyConsumption.getTitle();
            this.hashtags = worthyConsumption.getHashtags();
            this.detailImageUrl = worthyConsumption.getWorthyConsumptionUrl().getDetailImageUrl();
            this.detailBackgroundImageUrl = worthyConsumption.getWorthyConsumptionUrl().getDetailBackgroundImageUrl();
            this.videoUrl = worthyConsumption.getWorthyConsumptionUrl().getVideoUrl();
            this.whyRecommendation = worthyConsumption.getWhyRecommendation();
            this.originalPrice = worthyConsumption.getOriginalPrice();
            this.salePrice = worthyConsumption.getSalePrice();
            this.priceTag = worthyConsumption.getPriceTag();
            this.placeImageUrl = worthyConsumption.getWorthyConsumptionUrl().getPlaceImageUrl();
            this.placeTag = worthyConsumption.getPlaceTag();
        }


    }
//    public static class ReadDetailResponse extends ReadBasicResponse{
//        private int maxParticipants;
//        private String detailImageUrl;
//        private String detailBackgroundImageUrl;
//        private List<String> whyRecommendation;
//        private String placeImageUrl;
//        private String placeTag;
//
//        public ReadDetailResponse(WorthyConsumption worthyConsumption) {
//            super(worthyConsumption);
//            this.maxParticipants = worthyConsumption.getCondition().getMaxParticipants();
//            this.detailImageUrl = worthyConsumption.getWorthyConsumptionUrl().getDetailImageUrl();
//            this.detailBackgroundImageUrl = worthyConsumption.getWorthyConsumptionUrl().getDetailBackgroundImageUrl();
//            this.whyRecommendation = worthyConsumption.getWhyRecommendation();
//            this.placeImageUrl = worthyConsumption.getWorthyConsumptionUrl().getPlaceImageUrl();
//            this.placeTag = worthyConsumption.getPlaceTag();
//        }
//    }
}