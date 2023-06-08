package projectbuildup.mivv.domain.worthyConsumption.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import projectbuildup.mivv.domain.worthyConsumption.entity.Condition;
import projectbuildup.mivv.domain.worthyConsumption.entity.RecommendationReason;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumptionUrl;

import java.time.LocalDate;
import java.util.List;
@Schema(description = "가치소비 DTO")
public class WorthyConsumptionDto {
    @Getter @Setter @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Creation extends WorthyConsumptionUrlDto.Creation {
        @NotBlank(message = "가치소비의 이름을 입력해주세요")
        String title;
        @NotNull
        List<String> hashtags;
        @NotNull(message = "가치소비의 원래 가격을 입력해주세요") @Positive
        Integer originalPrice;
        @NotNull(message = "가치소비의 할인 가격을 입력해주세요") @Positive
        Integer salePrice;
        @NotNull
        List<RecommendationReasonDto> recommendationReasons;
        String availablePrice;
        String availablePlace;
        private Integer maxIssuance;

        @NotNull(message = "가치소비의 협약 시작 날짜를 입력해주세요")
        @Schema(example = "2001-03-02")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private LocalDate conventionStartDate;

        @NotNull(message = "가치소비의 협약 끝 날짜를 입력해주세요")
        @Schema(example = "2101-03-02")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private LocalDate conventionEndDate;

        private Long lastMonthAmount;
        public WorthyConsumption toEntity(WorthyConsumptionUrl worthyConsumptionUrl, Condition condition, List<RecommendationReason> recommendationReasons){
            return WorthyConsumption.builder()
                    .title(title)
                    .hashtags(hashtags)
                    .worthyConsumptionUrl(worthyConsumptionUrl)
                    .originalPrice(originalPrice)
                    .salePrice(salePrice)
                    .availablePrice(availablePrice)
                    .availablePlace(availablePlace)
                    .condition(condition)
                    .build();
        }
    }
    @Data @AllArgsConstructor
    public class Update extends WorthyConsumptionUrlDto.Update{
        String title;
        @NotNull
        List<String> hashtags;
        Integer originalPrice;
        Integer salePrice;
        @NotNull
        List<RecommendationReason> recommendationReasons;
        String availablePrice;
        String availablePlace;
        private Integer maxIssuance;

        @Schema(example = "2001-03-02")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private LocalDate conventionStartDate;

        @Schema(example = "2101-03-02")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private LocalDate conventionEndDate;
        private Long lastMonthAmount;
    }
    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class RecommendationReasonDto{
        String title;
        String description;
    }
}
