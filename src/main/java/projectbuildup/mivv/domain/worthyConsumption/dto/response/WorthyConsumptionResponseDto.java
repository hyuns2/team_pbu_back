package projectbuildup.mivv.domain.worthyConsumption.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.coupon.entity.CouponType;
import projectbuildup.mivv.domain.worthyConsumption.entity.CheckConditionType;
import projectbuildup.mivv.domain.worthyConsumption.entity.RecommendationReason;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "가치소비 응답 DTO")
public class WorthyConsumptionResponseDto {
    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter
    public static class ReadSummaryResponse {
        private Long id;
        private String title;
        private Integer originalPrice;
        private Integer salePrice;
        private String availablePrice;
        private Long lastMonthAmount;
        private Integer maxIssuance;
        private List<String> summary;
        private String logoPath;

        public ReadSummaryResponse(WorthyConsumption worthyConsumption, Coupon coupon) {
            this.id = worthyConsumption.getId();
            this.title = worthyConsumption.getTitle();
            this.originalPrice = worthyConsumption.getOriginalPrice();
            this.salePrice = worthyConsumption.getSalePrice();
            this.availablePrice = worthyConsumption.getAvailablePrice();
            this.lastMonthAmount = worthyConsumption.getCondition().getLastMonthAmount();
            this.maxIssuance = worthyConsumption.getCondition().getMaxIssuance();
            this.summary = coupon.getSummary();
            this.logoPath = worthyConsumption.getWorthyConsumptionUrl().getLogoPath();
        }
    }
    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter
    public static class ReadBasicResponse extends ReadSummaryResponse{
        private List<String> hashtags;
        private String videoThumbNailPath;
        private String videoPath;
        private String imagePath;
        private CheckConditionType checkConditionType;
        private Boolean isLiked;
        private Long couponId;
        private LocalDate conventionStartDate;
        private LocalDate conventionEndDate;
        private CouponType couponType;

        public ReadBasicResponse(WorthyConsumption worthyConsumption, Boolean isLiked, Coupon coupon) {
            super(worthyConsumption, coupon);
            this.videoThumbNailPath = worthyConsumption.getWorthyConsumptionUrl().getVideoThumbNailPath();
            this.hashtags = worthyConsumption.getHashtags();
            this.videoPath = worthyConsumption.getWorthyConsumptionUrl().getVideoPath();
            this.imagePath = worthyConsumption.getWorthyConsumptionUrl().getImagePath();
            this.checkConditionType = worthyConsumption.getCondition().getCheckConditionType();
            this.isLiked = isLiked;
            this.couponId = coupon.getId();
            this.conventionStartDate = worthyConsumption.getCondition().getConventionStartDate();
            this.conventionEndDate = worthyConsumption.getCondition().getConventionEndDate();
            this.couponType = coupon.getCouponType();
        }
    }
    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter
    @Builder
    public static class ReadDetailResponse{
        private Long id;
        private String title;
        private List<String> hashtags;
        private String logoPath;
        private String videoThumbNailPath;
        private String detailImageUrl;
        private String detailBackgroundImageUrl;
        private String videoUrl;
        private List<RecommendationReasonDto> recommendationReasons;
        private Integer originalPrice;
        private Integer salePrice;
        private String availablePrice;
        private String placeImageUrl;
        private String availablePlace;

        public ReadDetailResponse(WorthyConsumption worthyConsumption) {
            this.id = worthyConsumption.getId();
            this.title = worthyConsumption.getTitle();
            this.hashtags = worthyConsumption.getHashtags();
            this.logoPath = worthyConsumption.getWorthyConsumptionUrl().getLogoPath();
            this.videoThumbNailPath = worthyConsumption.getWorthyConsumptionUrl().getVideoThumbNailPath();
            this.detailImageUrl = worthyConsumption.getWorthyConsumptionUrl().getDetailImagePath();
            this.detailBackgroundImageUrl = worthyConsumption.getWorthyConsumptionUrl().getDetailBackgroundImagePath();
            this.videoUrl = worthyConsumption.getWorthyConsumptionUrl().getVideoPath();
            this.recommendationReasons = worthyConsumption.getRecommendationReasons().stream()
                    .map(RecommendationReasonDto::new)
                    .collect(Collectors.toList());
            this.originalPrice = worthyConsumption.getOriginalPrice();
            this.salePrice = worthyConsumption.getSalePrice();
            this.availablePrice = worthyConsumption.getAvailablePrice();
            this.placeImageUrl = worthyConsumption.getWorthyConsumptionUrl().getPlaceImagePath();
            this.availablePlace = worthyConsumption.getAvailablePlace();
        }


    }
    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class RecommendationReasonDto{
        String title;
        String description;
        public RecommendationReasonDto(RecommendationReason recommendationReason){
            this.title = recommendationReason.getTitle();
            this.description = recommendationReason.getDescription();
        }
    }
}