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
        private String priceTag;
        private Long availablePrice;

        private Integer maxIssuance;
        private List<String> summary;
        private String logoPath;

        boolean issueCountLimit;

        Boolean liked;

        public ReadSummaryResponse(WorthyConsumption worthyConsumption, Coupon coupon, Boolean liked) {
            this.id = worthyConsumption.getId();
            this.title = worthyConsumption.getTitle();
            this.originalPrice = worthyConsumption.getOriginalPrice();
            this.salePrice = worthyConsumption.getSalePrice();
            this.priceTag = worthyConsumption.getPriceTag();
            this.availablePrice = worthyConsumption.getCondition().getAvailablePrice();
            //this.issueCountLimit
            this.maxIssuance = worthyConsumption.getCondition().getMaxIssuance();
            this.summary = coupon.getSummary();
            this.logoPath = worthyConsumption.getWorthyConsumptionUrl().getLogoPath();

            this.liked = liked;
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
        private Long couponId;
        private LocalDate conventionStartDate;
        private LocalDate conventionEndDate;

        private LocalDate issuableStartDate;
        private LocalDate issuableEndDate;

        private CouponType couponType;
        private long issueCount;

        public ReadBasicResponse(WorthyConsumption worthyConsumption, Coupon coupon, Boolean isLiked, long issueCount) {
            super(worthyConsumption, coupon, isLiked);
            this.hashtags = worthyConsumption.getHashtags();

            this.videoThumbNailPath = worthyConsumption.getWorthyConsumptionUrl().getVideoThumbNailPath();
            this.videoPath = worthyConsumption.getWorthyConsumptionUrl().getVideoPath();
            this.imagePath = worthyConsumption.getWorthyConsumptionUrl().getImagePath();

            this.checkConditionType = worthyConsumption.getCondition().getCheckConditionType();
            this.couponId = coupon.getId();
            this.conventionStartDate = worthyConsumption.getCondition().getConventionStartDate();
            this.conventionEndDate = worthyConsumption.getCondition().getConventionEndDate();
            this.issuableStartDate = coupon.getIssuableStartDate();
            this.issuableEndDate = coupon.getIssuableEndDate();
            this.couponType = coupon.getCouponType();
            this.issueCount = issueCount;
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
        private String videoPath;
        private String videoThumbNailPath;
        private String imagePath;
        private String detailImagePath;
        private String detailBackgroundImagePath;
        private String placeImagePath;


        private List<RecommendationReasonDto> recommendationReasons;
        private Integer originalPrice;
        private Integer salePrice;
        private String priceTag;

        private String availablePlace;
        private String availablePlaceDetail;

        public ReadDetailResponse(WorthyConsumption worthyConsumption) {
            this.id = worthyConsumption.getId();
            this.title = worthyConsumption.getTitle();
            this.hashtags = worthyConsumption.getHashtags();

            this.logoPath = worthyConsumption.getWorthyConsumptionUrl().getLogoPath();
            this.videoPath = worthyConsumption.getWorthyConsumptionUrl().getVideoPath();
            this.videoThumbNailPath = worthyConsumption.getWorthyConsumptionUrl().getVideoThumbNailPath();
            this.imagePath = worthyConsumption.getWorthyConsumptionUrl().getImagePath();
            this.detailImagePath = worthyConsumption.getWorthyConsumptionUrl().getDetailImagePath();
            this.detailBackgroundImagePath = worthyConsumption.getWorthyConsumptionUrl().getDetailBackgroundImagePath();
            this.placeImagePath = worthyConsumption.getWorthyConsumptionUrl().getPlaceImagePath();

            this.recommendationReasons = worthyConsumption.getRecommendationReasons().stream()
                    .map(RecommendationReasonDto::new)
                    .collect(Collectors.toList());
            this.originalPrice = worthyConsumption.getOriginalPrice();
            this.salePrice = worthyConsumption.getSalePrice();
            this.priceTag = worthyConsumption.getPriceTag();

            this.availablePlace = worthyConsumption.getAvailablePlace();
            this.availablePlaceDetail = worthyConsumption.getAvailablePlaceDetail();
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