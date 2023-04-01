package projectbuildup.mivv.domain.coupon.dto.request;

import lombok.Builder;
import lombok.Getter;
import projectbuildup.mivv.domain.coupon.entity.Coupon;

import java.time.LocalDate;

public class CouponRequestDto {
    @Builder
    @Getter
    public static class creationRequest{
        String title;
        String content;
        String imageUrl;
        int pin;
        LocalDate limitDate;
        int originalPrice;
        int salePrice;

        public Coupon toEntity(){
            return Coupon.builder()
                    .title(title)
                    .content(content)
                    .imageUrl(imageUrl)
                    .pin(pin)
                    .limitDate(limitDate)
                    .originalPrice(originalPrice)
                    .salePrice(salePrice)
                    .build();
        }

    }
}
