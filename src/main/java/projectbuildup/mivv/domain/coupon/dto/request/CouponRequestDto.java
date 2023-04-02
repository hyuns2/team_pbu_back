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
    public static class CreationRequest{
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
    public static class ReadRequest{
        Long id;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class UpdateContentRequest{
        String title;
        String content;
        String imageUrl;

    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class UpdatePriceRequest{
        int originalPrice;
        int salePrice;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class DeleteRequest{
        Long id;
    }
}
