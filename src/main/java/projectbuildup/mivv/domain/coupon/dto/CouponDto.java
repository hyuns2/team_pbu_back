package projectbuildup.mivv.domain.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.coupon.entity.CouponType;

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
        @NotNull
        @Schema(description = "쿠폰의 설명 요약")
        private List<String> summary;
        @NotNull
        @Schema(description = "쿠폰 종류")
        private CouponType couponType;
        @NotNull
        private Boolean issueCountLimit;
        @NotNull
        private String howToUse;
        @NotNull
        private List<String> caution;
        @NotNull
        private String priceTag;
        @NotNull
        private Long availablePrice;
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        private Long id;

        String logoPath;
        String imagePath;

        String title;
        int originalPrice;
        int salePrice;
        String priceTag;

        LocalDate limitStartDate;
        LocalDate limitEndDate;

        CouponType couponType;

        String howToUse;
        List<String> caution;
        List<String> summary;
        @NotNull
        private Long availablePrice;

        public Response(Coupon coupon){
            this.id = coupon.getId();

            this.title = coupon.getTitle();
            this.originalPrice = coupon.getWorthyConsumption().getOriginalPrice();
            this.salePrice = coupon.getWorthyConsumption().getSalePrice();
            this.priceTag = coupon.getPriceTag();

            this.logoPath = coupon.getWorthyConsumption().getWorthyConsumptionUrl().getLogoPath();
            this.imagePath = coupon.getImagePath();

            this.limitStartDate = coupon.getLimitStartDate();
            this.limitEndDate = coupon.getLimitEndDate();

            this.couponType = coupon.getCouponType();
            this.howToUse = coupon.getHowToUse();
            this.caution = coupon.getCaution();
            this.summary = coupon.getSummary();
            this.availablePrice = coupon.getAvailablePrice();
        }
    }
}
