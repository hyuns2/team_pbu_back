package projectbuildup.mivv.domain.coupon.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.coupon.dto.CouponDto;
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

    public Coupon(CouponDto.Creation couponDto,String imagePath) {
        this.title = couponDto.getTitle();
        this.imagePath = imagePath;
        this.pin = couponDto.getPin();
        this.limitStartDate = couponDto.getLimitStartDate();
        this.limitEndDate = couponDto.getLimitEndDate();
    }
    public void update(CouponDto.Update couponDto, String imagePath){
        this.title = couponDto.getTitle();
        this.imagePath = imagePath;
        this.pin = couponDto.getPin();
        this.limitStartDate = couponDto.getLimitStartDate();
        this.limitEndDate = couponDto.getLimitEndDate();
    }


}
