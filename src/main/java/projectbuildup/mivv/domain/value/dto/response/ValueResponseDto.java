package projectbuildup.mivv.domain.value.dto.response;

import lombok.*;
import projectbuildup.mivv.domain.value.dto.request.ValueRequestDto;
import projectbuildup.mivv.domain.value.entity.Value;

import java.util.List;

public class ValueResponseDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ReadSummaryResponse {
        private String title;
        private int originalPrice;
        private int salePrice;
        private String priceTag;
        private List<String> summary;

        public ReadSummaryResponse(Value value) {
            this.title = value.getTitle();
            this.originalPrice = value.getOriginalPrice();
            this.salePrice = value.getSalePrice();
            this.priceTag = value.getPriceTag();
            this.summary = value.getSummary();
        }
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ReadBasicResponse extends ReadSummaryResponse{
        private List<String> hashtags;
        private String videoUrl;
        private String imageUrl;

        public ReadBasicResponse(Value value) {
            this.hashtags = value.getHashtags();
            this.videoUrl = value.getValueUrl().getVideoUrl();
            this.imageUrl = value.getValueUrl().getImageUrl();
        }

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ReadDetailResponse extends ReadBasicResponse{
        private int maxParticipants;
        private String detailImageUrl;
        private String detailBackgroundImageUrl;
        private List<String> whyRecommendation;
        private String placeImageUrl;
        private String placeTag;

        public ReadDetailResponse(Value value) {
            this.maxParticipants = value.getMaxParticipants();
            this.detailImageUrl = value.getValueUrl().getDetailImageUrl();
            this.detailBackgroundImageUrl = value.getValueUrl().getDetailBackgroundImageUrl();
            this.whyRecommendation = value.getWhyRecommendation();
            this.placeImageUrl = value.getValueUrl().getPlaceImageUrl();
            this.placeTag = value.getPlaceTag();
        }

    }
}