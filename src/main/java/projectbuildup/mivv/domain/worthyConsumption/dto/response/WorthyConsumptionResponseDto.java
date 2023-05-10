package projectbuildup.mivv.domain.worthyConsumption.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import projectbuildup.mivv.domain.worthyConsumption.entity.CheckConditionType;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;

import java.util.List;
@Schema(description = "가치소비 응답 DTO")
public class WorthyConsumptionResponseDto {
    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter
    public static class ReadSummaryResponse {
        private String title;
        private Integer originalPrice;
        private Integer salePrice;
        private String availablePrice;
        private Long lastMonthAmount;
        private Integer maxParticipants;
        private String summary;

        public ReadSummaryResponse(WorthyConsumption worthyConsumption) {
            this.title = worthyConsumption.getTitle();
            this.originalPrice = worthyConsumption.getOriginalPrice();
            this.salePrice = worthyConsumption.getSalePrice();
            this.availablePrice = worthyConsumption.getAvailablePrice();
            this.lastMonthAmount = worthyConsumption.getCondition().getLastMonthAmount();
            this.maxParticipants = worthyConsumption.getCondition().getMaxParticipants();
            this.summary = worthyConsumption.getSummary();
        }
    }
    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter
    public static class ReadBasicResponse extends ReadSummaryResponse{
        private List<String> hashtags;
        private String videoPath;
        private String imagePath;
        private CheckConditionType isIssuableCoupon;
        private Boolean isLiked;//이거 처리 해야 됨

        public ReadBasicResponse(WorthyConsumption worthyConsumption, Boolean isLiked) {
            super(worthyConsumption);
            this.hashtags = worthyConsumption.getHashtags();
            this.videoPath = worthyConsumption.getWorthyConsumptionUrl().getVideoPath();
            this.imagePath = worthyConsumption.getWorthyConsumptionUrl().getImagePath();
            this.isIssuableCoupon = worthyConsumption.getCondition().getIsIssuableCoupon();
            this.isLiked = isLiked;
        }

        public ReadBasicResponse(WorthyConsumption worthyConsumption) {
            super(worthyConsumption);
            this.hashtags = worthyConsumption.getHashtags();
            this.videoPath = worthyConsumption.getWorthyConsumptionUrl().getVideoPath();
            this.imagePath = worthyConsumption.getWorthyConsumptionUrl().getImagePath();
            this.isIssuableCoupon = worthyConsumption.getCondition().getIsIssuableCoupon();
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
        private List<String> recommendationReason;
        private Integer originalPrice;
        private Integer salePrice;
        private String availablePrice;
        private String placeImageUrl;
        private String availablePlace;

        public ReadDetailResponse(WorthyConsumption worthyConsumption) {
            this.title = worthyConsumption.getTitle();
            this.hashtags = worthyConsumption.getHashtags();
            this.detailImageUrl = worthyConsumption.getWorthyConsumptionUrl().getDetailImagePath();
            this.detailBackgroundImageUrl = worthyConsumption.getWorthyConsumptionUrl().getDetailBackgroundImagePath();
            this.videoUrl = worthyConsumption.getWorthyConsumptionUrl().getVideoPath();
            this.recommendationReason = worthyConsumption.getRecommendationReason();
            this.originalPrice = worthyConsumption.getOriginalPrice();
            this.salePrice = worthyConsumption.getSalePrice();
            this.availablePrice = worthyConsumption.getAvailablePrice();
            this.placeImageUrl = worthyConsumption.getWorthyConsumptionUrl().getPlaceImagePath();
            this.availablePlace = worthyConsumption.getAvailablePlace();
        }


    }
}