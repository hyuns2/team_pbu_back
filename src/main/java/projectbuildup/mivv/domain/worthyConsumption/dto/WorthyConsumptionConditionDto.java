package projectbuildup.mivv.domain.worthyConsumption.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionRequestDto;

import java.time.LocalDate;
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "가치소비의 조건 DTO")
public class WorthyConsumptionConditionDto {
    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "가치소비 조건 생성 DTO")
    public static class CreationRequest {
        @NotNull
        @Positive
        private int maxParticipants;
        @NotNull(message = "가치소비의 쿠폰 발급 가능 시작 날짜를 입력해주세요")
        @Schema(example = "2001-03-02")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private LocalDate issuableCouponStartDate;
        @NotNull(message = "가치소비의 쿠폰 발급 가능 시작 날짜를 입력해주세요")
        @Schema(example = "2001-03-02")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private LocalDate issuableCouponEndDate;
        @NotNull
        @Positive
        private int lastMonthAmount;
    }
    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "가치소비 조건 중 날짜 수정 DTO")
    public static class UpdateIssuableCouponDateRequest extends WorthyConsumptionRequestDto.IdRequest{
        @NotNull(message = "가치소비의 쿠폰 발급 가능 시작 날짜를 입력해주세요")
        @Schema(example = "2023-04-13")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        //@DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate issuableCouponStartDate;
        @NotNull(message = "가치소비의 쿠폰 발급 가능 종료 날짜를 입력해주세요")
        @Schema(example = "2023-04-13")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        //@DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate issuableCouponEndDate;

    }
    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "가치소비 조건 중 인원과 금액 관련 수정 DTO")
    public static class UpdateConditionRequest extends WorthyConsumptionRequestDto.IdRequest{
        @NotNull(message = "가치소비의 쿠폰 발급 가능 인원을 입력해주세요")
        int maxParticipants;
        @NotNull(message = "가치소비의 쿠폰 발급 가능 전월 달성 금액을 입력해주세요")
        int lastMonthAmount;

    }
}