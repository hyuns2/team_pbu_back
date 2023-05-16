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
        LocalDate limitStartDate;
        LocalDate limitEndDate;
        public ReadResponse(Coupon coupon){
            this.title = coupon.getTitle();
            this.imageUrl = coupon.getImageUrl();
            this.limitStartDate = coupon.getLimitStartDate();
            this.limitEndDate = coupon.getLimitEndDate();
        }
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReadResponseWithWorthyConsumption{
        String title;
        int originalPrice;
        int salePrice;
        String priceTag;
        String imageUrl;
        LocalDate limitStartDate;
        LocalDate limitEndDate;
        public ReadResponseWithWorthyConsumption(Coupon coupon){
            this.title = coupon.getTitle();
            this.originalPrice = coupon.getWorthyConsumption().getOriginalPrice();
            this.salePrice = coupon.getWorthyConsumption().getSalePrice();
            this.priceTag = coupon.getWorthyConsumption().getPriceTag();
            this.imageUrl = coupon.getImageUrl();
            this.limitStartDate = coupon.getLimitStartDate();
            this.limitEndDate = coupon.getLimitEndDate();
        }
    }
}
