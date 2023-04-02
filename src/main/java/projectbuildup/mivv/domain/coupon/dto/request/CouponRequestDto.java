package projectbuildup.mivv.domain.coupon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.mivv.domain.coupon.entity.Coupon;

import java.time.LocalDate;

public class CouponRequestDto {
    @AllArgsConstructor
    @NoArgsConstructor
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
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class readRequest{
        Long id;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class updateContentRequest{
        String title;
        String content;
        String imageUrl;

    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class updatePriceRequest{
        int originalPrice;
        int salePrice;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class deleteRequest{
        Long id;
    }
}
