package projectbuildup.mivv.domain.worthyConsumption.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionRequestDto;

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
    @NonNull
    private int lastMonthAmount;
    @OneToOne(mappedBy = "worthyConsumptionUrl", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private WorthyConsumption worthyConsumption;

    public void updateCondition(WorthyConsumptionRequestDto.UpdateConditionRequest worthyConsumptionRequestDto){
        this.maxParticipants = worthyConsumptionRequestDto.getMaxParticipants();
        this.lastMonthAmount = worthyConsumptionRequestDto.getLastMonthAmount();
    }
    public void updateIssuableCouponDate(WorthyConsumptionRequestDto.UpdateIssuableCouponDateRequest requestWorthyConsumptionDto){
        this.issuableCouponStartDate = requestWorthyConsumptionDto.getIssuableCouponStartDate();
        this.issuableCouponEndDate = requestWorthyConsumptionDto.getIssuableCouponEndDate();
    }
}
