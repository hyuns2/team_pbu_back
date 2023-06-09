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
    @Column(name = "max_issuance")
    private int maxIssuance;
    @NonNull
    @Column(name = "available_price")
    private long availablePrice;
    @Enumerated(EnumType.STRING)
    @Column(name = "check_condition_type")
    private CheckConditionType checkConditionType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "convention_start_date")
    private LocalDate conventionStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "convention_end_date")
    private LocalDate conventionEndDate;

    public Condition(WorthyConsumptionDto.Request worthyConsumptionDto){
        this.maxIssuance= worthyConsumptionDto.getMaxIssuance();
        this.availablePrice = worthyConsumptionDto.getAvailablePrice();
        this.conventionStartDate = worthyConsumptionDto.getConventionStartDate();
        this.conventionEndDate = worthyConsumptionDto.getConventionEndDate();
    }
    public void update(WorthyConsumptionDto.Request worthyConsumptionDto){
        this.maxIssuance= worthyConsumptionDto.getMaxIssuance();
        this.availablePrice = worthyConsumptionDto.getAvailablePrice();
        this.conventionStartDate = worthyConsumptionDto.getConventionStartDate();
        this.conventionEndDate = worthyConsumptionDto.getConventionEndDate();
    }
    public void checkIssuableCouponStatus(CheckConditionType conditionType){
        this.checkConditionType = conditionType;
    }
}
