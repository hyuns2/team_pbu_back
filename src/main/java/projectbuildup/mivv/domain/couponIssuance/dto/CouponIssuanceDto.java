package projectbuildup.mivv.domain.couponIssuance.dto;

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
        int pin;
    }
}
