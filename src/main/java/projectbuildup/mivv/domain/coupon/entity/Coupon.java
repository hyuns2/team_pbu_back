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
    @Column(name = "CouponId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worthyConsumptionId")
    @Setter
    private WorthyConsumption worthyConsumption;

    @Nonnull
    private String title;
    @Nonnull
    private String imagePath;
    @Nonnull
    private int pin;
    @Nonnull
    private LocalDate limitStartDate;
    @Nonnull
    private LocalDate limitEndDate;

    public Coupon(CouponRequestDto.CreationRequest couponRequestDto,String imagePath) {
        this.title = couponRequestDto.getTitle();
        this.imagePath = imagePath;
        this.pin = couponRequestDto.getPin();
        this.limitStartDate = couponRequestDto.getLimitStartDate();
        this.limitEndDate = couponRequestDto.getLimitEndDate();
    }

    public void updateContent(CouponRequestDto.UpdateContentRequest couponRequestDto, String imagePath){
        this.title = couponRequestDto.getTitle();
        this.imagePath = imagePath;
    }
    public void updateDate(CouponRequestDto.UpdateDateRequest couponRequestDto){
        this.limitStartDate = couponRequestDto.getLimitStartDate();
        this.limitEndDate = couponRequestDto.getLimitEndDate();

    }


}
