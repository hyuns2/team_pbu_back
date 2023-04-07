package projectbuildup.mivv.domain.coupon.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.coupon.dto.request.CouponRequestDto;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.time.LocalDate;
@Entity @Table(name = "Coupon")
@Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Coupon extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "worthyconsumptionId")
    private WorthyConsumption worthyConsumption;
    @Nonnull
    private String title;
    @Nonnull
    private String imageUrl;
    @Nonnull
    private int pin;
    @Setter
    private Boolean use;
    @Nonnull
    private LocalDate limitStartDate;
    @Nonnull
    private LocalDate limitEndDate;
    public void updateContent(CouponRequestDto.UpdateContentRequest requestRequest){
        this.title = requestRequest.getTitle();
        this.imageUrl = requestRequest.getImageUrl();
    }


}
