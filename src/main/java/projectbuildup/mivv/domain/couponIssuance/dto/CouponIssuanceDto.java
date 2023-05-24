package projectbuildup.mivv.domain.couponIssuance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CouponIssuanceDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PinDto{
        @NotNull @Positive
        @Schema(description = "쿠폰의 pin 번호")
        int pin;
    }
}
