package projectbuildup.mivv.domain.coupon.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;

import java.time.LocalDate;

public class CouponRequestDto {
    @AllArgsConstructor @NoArgsConstructor
    @Builder @Getter
    @Schema(description = "쿠폰 생성 요청 DTO")
    public static class CreationRequest{
        @NotBlank(message = "쿠폰의 이름을 입력해주세요")
        private String title;
        @NotBlank(message = "쿠폰의 이미지를 입력해주세요")
        private MultipartFile image;
        @NotNull(message = "쿠폰의 핀 번호를 입력해주세요")
        private int pin;
        @NotNull @PastOrPresent
        private LocalDate limitStartDate;
        @NotNull @Future
        private LocalDate limitEndDate;
//        public Coupon toEntity(){
//            return Coupon.builder()
//                    .title(title)
//                    .image(image)
//                    .pin(pin)
//                    .limitStartDate(limitStartDate)
//                    .limitEndDate(limitEndDate)
//                    .build();
//        }
    }
    @AllArgsConstructor @NoArgsConstructor
    @Getter
    public static class UpdateContentRequest{
        @NotNull
        Long id;
        @NotBlank(message = "쿠폰의 이름을 입력해주세요")
        String title;
        @NotBlank(message = "쿠폰의 이미지를 입력해주세요")
        private MultipartFile image;

    }
    @AllArgsConstructor @NoArgsConstructor
    @Getter
    public static class UpdateDateRequest{
        @NotNull
        Long id;
        @NotNull @PastOrPresent
        LocalDate limitStartDate;
        @NotNull @Future
        LocalDate limitEndDate;
    }
}
