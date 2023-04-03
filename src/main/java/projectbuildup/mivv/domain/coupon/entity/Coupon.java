package projectbuildup.mivv.domain.coupon.entity;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.*;
import projectbuildup.mivv.domain.coupon.dto.request.CouponRequestDto;
import projectbuildup.mivv.domain.member.entity.User;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.time.LocalDate;
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Nonnull
    private Long valueId;
    @OneToMany
    private User user;
    @Nonnull
    private String title;
    @Nonnull
    private String content;
    @Nonnull
    private String imageUrl;
    @Nonnull
    private int pin;
    @Setter
    private Boolean use;
    @Nonnull
    private LocalDate limitDate;
    @Nonnull
    private int originalPrice;
    @Nonnull
    private int salePrice;

    public boolean isUsed(){
        return use;
    }
    public void updateContent(CouponRequestDto.UpdateContentRequest requestRequest){
        this.title = requestRequest.getTitle();
        this.content = requestRequest.getContent();
        this.imageUrl = requestRequest.getImageUrl();
    }
    public void updatePrice(CouponRequestDto.UpdatePriceRequest requestRequest){
        this.originalPrice = requestRequest.getOriginalPrice();
        this.salePrice = requestRequest.getSalePrice();
    }


}
