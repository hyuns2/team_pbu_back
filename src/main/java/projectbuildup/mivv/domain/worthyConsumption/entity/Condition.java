package projectbuildup.mivv.domain.worthyConsumption.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import projectbuildup.mivv.domain.worthyConsumption.dto.WorthyConsumptionDto;

import java.time.LocalDate;
@Entity @Table(name = "WorthyConsumptionCondition")
@AllArgsConstructor @NoArgsConstructor(force = true)
@Getter @Builder
public class Condition {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NonNull
    private int maxParticipants;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate issuableCouponStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate issuableCouponEndDate;
    @NonNull
    private long lastMonthAmount;
    @Setter
    @Enumerated(EnumType.STRING)
    private CheckConditionType isIssuableCoupon;

    public Condition(WorthyConsumptionDto.Creation worthyConsumptionDto){
        this.maxParticipants= worthyConsumptionDto.getMaxParticipants();
        this.lastMonthAmount = worthyConsumptionDto.getLastMonthAmount();
        this.issuableCouponStartDate = worthyConsumptionDto.getIssuableCouponStartDate();
        this.issuableCouponEndDate = worthyConsumptionDto.getIssuableCouponEndDate();
    }
    public void update(WorthyConsumptionDto.Update worthyConsumptionDto){
        this.maxParticipants= worthyConsumptionDto.getMaxParticipants();
        this.lastMonthAmount = worthyConsumptionDto.getLastMonthAmount();
        this.issuableCouponStartDate = worthyConsumptionDto.getIssuableCouponStartDate();
        this.issuableCouponEndDate = worthyConsumptionDto.getIssuableCouponEndDate();
    }
    public void checkIssuableCouponStatus(CheckConditionType conditionType){
        this.isIssuableCoupon = conditionType;
    }
}
