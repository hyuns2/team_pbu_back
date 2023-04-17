package projectbuildup.mivv.domain.couponIssuance.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CouponIssuanceDto {
    @Getter
    @NoArgsConstructor
    public static class PinDto{
        @NotNull @Positive
        int pin;
    }
}
