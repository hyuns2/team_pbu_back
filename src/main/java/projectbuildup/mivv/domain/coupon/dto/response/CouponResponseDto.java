package projectbuildup.mivv.domain.coupon.dto.response;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.mivv.domain.coupon.entity.Coupon;

import java.time.LocalDate;

public class CouponResponseDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReadResponse{
        String title;
        String content;
        String imageUrl;
        LocalDate limitDate;
        int originalPrice;
        int salePrice;
        public ReadResponse(Coupon coupon){
            this.title = coupon.getTitle();
            this.content = coupon.getContent();
            this.imageUrl = coupon.getImageUrl();
            this.limitDate = coupon.getLimitDate();
            this.originalPrice = coupon.getOriginalPrice();
            this.salePrice = coupon.getSalePrice();
        }
    }
}
