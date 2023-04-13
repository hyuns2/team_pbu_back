package projectbuildup.mivv.domain.worthyConsumption.entity;

import lombok.NonNull;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Condition {
    @NonNull
    private int maxParticipants;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Setter
    private LocalDate issuableCouponStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Setter
    private LocalDate issuableCouponEndDate;
    private int lastMonthAmount;
}
