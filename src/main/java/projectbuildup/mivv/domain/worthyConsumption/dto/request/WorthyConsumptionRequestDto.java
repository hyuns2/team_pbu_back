package projectbuildup.mivv.domain.worthyConsumption.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.worthyConsumption.dto.WorthyConsumptionConditionDto;
import projectbuildup.mivv.domain.worthyConsumption.entity.Condition;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumptionUrl;

import java.time.LocalDate;
import java.util.List;
@Schema(description = "가치소비 요청 DTO")
public class WorthyConsumptionRequestDto {
    @Builder @Getter
    @AllArgsConstructor @NoArgsConstructor
    @Schema(description = "가치소비 생성 요청 DTO")
    public static class CreationRequest extends WorthyConsumptionUrlDto.CreationRequest{
        //@NotBlank(message = "가치소비의 이름을 입력해주세요")
        String title;
        //@NotNull
        List<String> hashtags;
        //@NotNull(message = "가치소비의 원래 가격을 입력해주세요") @Positive
        Integer originalPrice;
        //@NotNull(message = "가치소비의 할인 가격을 입력해주세요") @Positive
        Integer salePrice;
        //@NotNull
        List<String> whyRecommendation;
        String priceTag;
        String placeTag;
        //@NotNull
        String summary;
        //@Setter
        //WorthyConsumptionUrlDto.CreationRequest worthyConsumptionUrlDto;
        //@NotNull
        //WorthyConsumptionConditionDto.CreationRequest worthyConsumptionConditionDto;

        private Integer maxParticipants;

        //@NotNull(message = "가치소비의 쿠폰 발급 가능 시작 날짜를 입력해주세요")
        @Schema(example = "2001-03-02")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private LocalDate issuableCouponStartDate;

        //@NotNull(message = "가치소비의 쿠폰 발급 가능 시작 날짜를 입력해주세요")
        @Schema(example = "2001-03-02")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private LocalDate issuableCouponEndDate;

        //@NotNull @Positive
        private Long lastMonthAmount;
        public WorthyConsumption toEntity(WorthyConsumptionUrl worthyConsumptionUrl, Condition condition){
            return WorthyConsumption.builder()
                    .title(title)
                    .hashtags(hashtags)
                    .worthyConsumptionUrl(worthyConsumptionUrl)
                    .originalPrice(originalPrice)
                    .salePrice(salePrice)
                    .whyRecommendation(whyRecommendation)
                    .priceTag(priceTag)
                    .placeTag(placeTag)
                    .summary(summary)
                    .condition(condition)
                    .build();
        }
    }
    @NoArgsConstructor
    @Getter @Setter
    @AllArgsConstructor
    public static class IdRequest {
        @NotNull
        Long id;
    }
    @AllArgsConstructor @NoArgsConstructor
    @Getter @Setter
    @Builder
    public static class UpdateContentRequest extends WorthyConsumptionRequestDto.IdRequest{
        @NotBlank(message = "가치소비의 이름을 입력해주세요")
        String title;
        List<String> hashtags;
        Integer maxParticipants;
        List<String> whyRecommendation;
        String summary;
    }
    @NoArgsConstructor
    @Getter @Setter
    @AllArgsConstructor
    @Builder
    public static class UpdateUrlRequest extends WorthyConsumptionRequestDto.IdRequest {
        @NotNull
        String videoPath;
        @NotNull
        private MultipartFile image;
        @NotNull
        private MultipartFile detailImage;
        @NotNull
        private MultipartFile detailBackgroundImage;
        @NotNull
        private MultipartFile placeImage;
    }
    @NoArgsConstructor
    @Getter @Setter
    @AllArgsConstructor
    @Builder
    public static class UpdatePriceRequest extends WorthyConsumptionRequestDto.IdRequest{
        @NotNull(message = "가치소비의 원래 가격을 입력해주세요") @Positive
        Integer originalPrice;
        @NotNull(message = "가치소비의 할인 가격을 입력해주세요") @Positive
        Integer salePrice;
        String priceTag;

    }
    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter
    @Builder
    public static class UpdatePlaceRequest extends WorthyConsumptionRequestDto.IdRequest{
        @NotBlank(message = "가치소비의 위치 태그를 입력해주세요")
        String placeTag;

    }



}
