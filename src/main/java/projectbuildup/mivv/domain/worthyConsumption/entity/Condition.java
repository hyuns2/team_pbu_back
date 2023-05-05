package projectbuildup.mivv.domain.worthyConsumption.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import projectbuildup.mivv.domain.worthyConsumption.dto.WorthyConsumptionConditionDto;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionRequestDto;
import projectbuildup.mivv.global.error.exception.CBadRequestException;

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
    //@OneToOne(mappedBy = "Condition", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private WorthyConsumption worthyConsumption;
    @Setter
    @Enumerated(EnumType.STRING)
    private CheckConditionType isIssuableCoupon;
    public void updateCondition(WorthyConsumptionConditionDto.UpdateConditionRequest worthyConsumptionRequestDto){
        this.maxParticipants = worthyConsumptionRequestDto.getMaxParticipants();
        this.lastMonthAmount = worthyConsumptionRequestDto.getLastMonthAmount();
    }
    public void updateIssuableCouponDate(WorthyConsumptionConditionDto.UpdateIssuableCouponDateRequest requestWorthyConsumptionDto){
        this.issuableCouponStartDate = requestWorthyConsumptionDto.getIssuableCouponStartDate();
        this.issuableCouponEndDate = requestWorthyConsumptionDto.getIssuableCouponEndDate();
    }
    public Condition(WorthyConsumptionConditionDto.CreationRequest conditionDto){
        this.maxParticipants= conditionDto.getMaxParticipants();
        this.lastMonthAmount = conditionDto.getLastMonthAmount();
        this.issuableCouponStartDate = conditionDto.getIssuableCouponStartDate();
        this.issuableCouponEndDate = conditionDto.getIssuableCouponEndDate();
    }
    public void checkIssuableCouponStatus(CheckConditionType conditionType){
        this.isIssuableCoupon = conditionType;
    }
}