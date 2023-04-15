package projectbuildup.mivv.domain.worthyConsumption.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "가치소비의 조건 DTO")
public class WorthyConsumptionConditionDto {
    private int maxParticipants;
    private LocalDate issuableCouponStartDate;
    private LocalDate issuableCouponEndDate;
    private int lastMonthAmount;
}
