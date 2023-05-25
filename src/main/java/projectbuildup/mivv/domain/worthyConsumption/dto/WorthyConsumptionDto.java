package projectbuildup.mivv.domain.worthyConsumption.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import projectbuildup.mivv.domain.worthyConsumption.entity.Condition;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumptionUrl;

import java.time.LocalDate;
import java.util.List;
@Schema(description = "가치소비 DTO")
public class WorthyConsumptionDto {
    @Getter @Setter
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
        List<String> recommendationReason;
        String availablePrice;
        String availablePlace;
        @NotNull
        List<String> summary;
        private Integer maxParticipants;

        @NotNull(message = "가치소비의 쿠폰 발급 가능 시작 날짜를 입력해주세요")
        @Schema(example = "2001-03-02")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private LocalDate issuableCouponStartDate;

        @NotNull(message = "가치소비의 쿠폰 발급 가능 시작 날짜를 입력해주세요")
        @Schema(example = "2001-03-02")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private LocalDate issuableCouponEndDate;

        private Long lastMonthAmount;
        public WorthyConsumption toEntity(WorthyConsumptionUrl worthyConsumptionUrl, Condition condition){
            return WorthyConsumption.builder()
                    .title(title)
                    .hashtags(hashtags)
                    .worthyConsumptionUrl(worthyConsumptionUrl)
                    .originalPrice(originalPrice)
                    .salePrice(salePrice)
                    .recommendationReason(recommendationReason)
                    .availablePrice(availablePrice)
                    .availablePlace(availablePlace)
                    .summary(summary)
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
        List<String> recommendationReason;
        String availablePrice;
        String availablePlace;
        List<String> summary;
        private Integer maxParticipants;

        @Schema(example = "2001-03-02")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private LocalDate issuableCouponStartDate;

        @Schema(example = "2101-03-02")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private LocalDate issuableCouponEndDate;
        private Long lastMonthAmount;
    }
}
