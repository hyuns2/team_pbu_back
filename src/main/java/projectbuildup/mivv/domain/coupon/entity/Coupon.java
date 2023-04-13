package projectbuildup.mivv.domain.coupon.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.coupon.dto.request.CouponRequestDto;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.time.LocalDate;
@Entity @Table
@Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Coupon extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worthyConsumptionId")
    @Setter
    private WorthyConsumption worthyConsumption;
    @Nonnull
    private String title;
    @Nonnull
    private String imageUrl;
    @Nonnull
    private int pin;
    @Nonnull
    private LocalDate limitStartDate;
    @Nonnull
    private LocalDate limitEndDate;
    public void updateContent(CouponRequestDto.UpdateContentRequest couponRequestDto){
        this.title = couponRequestDto.getTitle();
        this.imageUrl = couponRequestDto.getImageUrl();
    }
    public void updateDate(CouponRequestDto.UpdateDateRequest couponRequestDto){
        this.limitStartDate = couponRequestDto.getLimitStartDate();
        this.limitEndDate = couponRequestDto.getLimitEndDate();

    }


}
