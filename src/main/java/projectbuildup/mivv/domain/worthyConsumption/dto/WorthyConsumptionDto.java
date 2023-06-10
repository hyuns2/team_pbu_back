package projectbuildup.mivv.domain.worthyConsumption.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.List;
@Schema(description = "가치소비 DTO")
public class WorthyConsumptionDto {
    @Getter @Setter @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Request extends WorthyConsumptionUrlDto.Request {
        @NotBlank(message = "가치소비의 이름을 입력해주세요")
        String title;
        @NotNull
        List<String> hashtags;
        @NotNull(message = "가치소비의 원래 가격을 입력해주세요") @Positive
        Integer originalPrice;
        @NotNull(message = "가치소비의 할인 가격을 입력해주세요") @Positive
        Integer salePrice;
        //@NotNull
        List<RecommendationReasonDto> recommendationReasons;
        @NotNull
        String availablePlace;
        @NotNull
        String availablePlaceDetail;

        //Condition
        @NotNull
        private Integer maxIssuance;
        @NotNull
        private Long availablePrice;
        @NotNull(message = "가치소비의 협약 시작 날짜를 입력해주세요")
        @Schema(example = "2001-03-02")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private LocalDate conventionStartDate;
        @NotNull(message = "가치소비의 협약 끝 날짜를 입력해주세요")
        @Schema(example = "2101-03-02")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private LocalDate conventionEndDate;

        public WorthyConsumption toEntity(WorthyConsumptionUrl worthyConsumptionUrl, Condition condition){
            return WorthyConsumption.builder()
                    .title(title)
                    .hashtags(hashtags)
                    .worthyConsumptionUrl(worthyConsumptionUrl)
                    .originalPrice(originalPrice)
                    .salePrice(salePrice)
                    .availablePlace(availablePlace)
                    .availablePlaceDetail(availablePlaceDetail)
                    .condition(condition)
                    .build();
        }
    }
    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter
    //@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class RecommendationReasonDto{
        String title;
        String description;
        public RecommendationReason toEntity(WorthyConsumption worthyConsumption){
            return RecommendationReason.builder()
                    .title(title)
                    .description(description)
                    .worthyConsumption(worthyConsumption)
                    .build();
        }
    }
}
