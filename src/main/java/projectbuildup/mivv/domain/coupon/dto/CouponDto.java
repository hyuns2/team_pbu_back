package projectbuildup.mivv.domain.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.coupon.entity.Coupon;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "쿠폰 DTO")
public class CouponDto {
    @Data
    @AllArgsConstructor
    @Schema(description = "쿠폰 request DTO")
    public class Request {
        @Schema(description = "쿠폰 이름")
        @NotBlank(message = "쿠폰의 이름을 입력해주세요")
        private String title;
        @Schema(description = "쿠폰 이미지")
        @NotNull
        private MultipartFile image;
        @Schema(description = "쿠폰 핀번호")
        @NotNull(message = "쿠폰의 핀 번호를 입력해주세요")
        private Integer pin;
        @NotNull @PastOrPresent
        @Schema(description = "쿠폰 유효기간의 시작 날짜")
        private LocalDate limitStartDate;
        @NotNull @Future
        @Schema(description = "쿠폰 유효기간의 종료 날짜")
        private LocalDate limitEndDate;
        @NotNull
        @Schema(description = "쿠폰 발급기간의 시작 날짜")
        private LocalDate issuableStartDate;
        @NotNull
        @Schema(description = "쿠폰 발급기간의 종료 날짜")
        private LocalDate issuableEndDate;
        private List<String> summary;
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        private Long id;
        String title;
        int originalPrice;
        int salePrice;
        String priceTag;
        private String imagePath;
        LocalDate limitStartDate;
        LocalDate limitEndDate;
        public Response(Coupon coupon){
            this.id = coupon.getId();
            this.title = coupon.getTitle();
            this.originalPrice = coupon.getWorthyConsumption().getOriginalPrice();
            this.salePrice = coupon.getWorthyConsumption().getSalePrice();
            this.priceTag = coupon.getWorthyConsumption().getAvailablePrice();
            this.imagePath = coupon.getImagePath();
            this.limitStartDate = coupon.getLimitStartDate();
            this.limitEndDate = coupon.getLimitEndDate();
        }
    }
}
