package projectbuildup.mivv.domain.value.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import projectbuildup.mivv.domain.challenge.dto.response.ChallengeResponseDto;
import projectbuildup.mivv.domain.value.entity.Value;

import java.util.List;

public class ValueResponseDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReadSummaryResponse {
        private String title;
        @NonNull
        private List<String> hashtags;
        @NonNull
        private String videoUrl;
        private String imageUrl;
        private int originalPrice;
        private int salePrice;
        private String priceTag;
        private List<String> summary;

        public ReadSummaryResponse(Value value){
            this.title = value.getTitle();
            this.hashtags = value.getHashtags();
            this.videoUrl = value.getVideoUrl();
            this.imageUrl = value.getImageUrl();
            this.originalPrice = value.getOriginalPrice();
            this.salePrice = value.getSalePrice();
            this.summary = value.getSummary();
        }
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReadDetailResponse {
        private String title;
        private List<String> hashtags;
        private int maxParticipants;
        private String videoUrl;
        private String imageUrl;
        private String detailImageUrl;
        private String detailBackgroundImageUrl;
        private int originalPrice;
        private int salePrice;
        private List<String> whyRecommendation;
        private String priceTag;
        private String placeImageUrl;
        private String placeTag;
        private List<String> summary;

        public ReadDetailResponse(Value value){
            this.title = value.getTitle();
            this.hashtags = value.getHashtags();
            this.maxParticipants = value.getMaxParticipants();
            this.videoUrl = value.getVideoUrl();
            this.imageUrl = value.getImageUrl();
            this.detailImageUrl = value.getDetailImageUrl();
            this.detailBackgroundImageUrl = value.getDetailBackgroundImageUrl();
            this.originalPrice = value.getOriginalPrice();
            this.salePrice = value.getSalePrice();
            this.whyRecommendation = value.getWhyRecommendation();
            this.priceTag = value.getPriceTag();
            this.placeImageUrl = value.getPlaceImageUrl();
            this.placeTag = value.getPlaceTag();
            this.summary = value.getSummary();
        }
    }
}
