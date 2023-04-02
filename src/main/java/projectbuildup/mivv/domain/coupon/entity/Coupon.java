package projectbuildup.mivv.domain.coupon.entity;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Null;
import lombok.*;
import projectbuildup.mivv.domain.coupon.dto.request.CouponRequestDto;
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
    Long id;
    @Nullable
    Long valueId;
    @Nonnull
    String title;
    @Nonnull
    String content;
    @Nonnull
    String imageUrl;
    @Nonnull
    int pin;
    @Setter
    Boolean use = false;
    @Nonnull
    LocalDate limitDate;
    @Nonnull
    int originalPrice;
    @Nonnull
    int salePrice;

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
