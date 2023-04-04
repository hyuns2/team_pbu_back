package projectbuildup.mivv.domain.coupon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.value.entity.Value;

import java.time.LocalDate;

public class CouponRequestDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class CreationRequest{
        String title;
        String imageUrl;
        int pin;
        LocalDate limitStartDate;
        LocalDate limitEndDate;
        public Coupon toEntity(){
            return Coupon.builder()
                    .title(title)
                    .imageUrl(imageUrl)
                    .pin(pin)
                    .limitStartDate(limitStartDate)
                    .limitEndDate(limitEndDate)
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
        Long id;
        String title;
        String content;
        String imageUrl;

    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class UpdatePriceRequest{
        Long id;
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
