package projectbuildup.mivv.domain.worthyConsumption.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import projectbuildup.mivv.domain.worthyConsumption.dto.WorthyConsumptionDto;

import java.time.LocalDate;
@Entity @Table(name = "worthy_consumption_condition")
@AllArgsConstructor @NoArgsConstructor(force = true)
@Getter @Builder
public class Condition {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @NonNull
    @Column(name = "max_participants")
    private int maxParticipants;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "issuable_coupon_start_date")
    private LocalDate issuableCouponStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "issuable_coupon_end_date")
    private LocalDate issuableCouponEndDate;
    @NonNull
    @Column(name = "last_month_amount")
    private long lastMonthAmount;
    @Enumerated(EnumType.STRING)
    @Column(name = "check_condition_type")
    private CheckConditionType checkConditionType;

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
        this.checkConditionType = conditionType;
    }
}
