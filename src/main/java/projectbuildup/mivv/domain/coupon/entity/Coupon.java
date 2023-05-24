package projectbuildup.mivv.domain.coupon.entity;

import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.coupon.dto.CouponDto;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.time.LocalDate;
@Entity @Table(name = "coupon")
@Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Coupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worthy_consumption_id")
    @Setter
    private WorthyConsumption worthyConsumption;
    @Column(name = "title", nullable = false, length = 30)
    private String title;
    @Column(name = "image_path", nullable = false)
    private String imagePath;
    @Column(name = "pin", nullable = false, length = 10)
    private int pin;
    @Column(name = "limit_start_date", nullable = false)
    private LocalDate limitStartDate;
    @Column(name = "limit_end_date", nullable = false)
    private LocalDate limitEndDate;

    public static Coupon toEntity(CouponDto.Request couponDto,String imagePath){
        return Coupon.builder()
                .title(couponDto.getTitle())
                .imagePath(imagePath)
                .pin(couponDto.getPin())
                .limitStartDate(couponDto.getLimitStartDate())
                .limitEndDate(couponDto.getLimitEndDate())
                .build();
    }
    public void update(CouponDto.Request couponDto, String imagePath){
        this.title = couponDto.getTitle();
        this.imagePath = imagePath;
        this.pin = couponDto.getPin();
        this.limitStartDate = couponDto.getLimitStartDate();
        this.limitEndDate = couponDto.getLimitEndDate();
    }


}
