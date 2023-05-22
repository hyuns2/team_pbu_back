package projectbuildup.mivv.domain.coupon.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Schema(description = "쿠폰 DTO")
public class CouponDto {
    @Data @AllArgsConstructor
    @Schema(description = "쿠폰 생성 DTO")
    public class Creation {
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
    }
    @AllArgsConstructor @Data
    @Schema(description = "쿠폰 수정 DTO")
    public class Update{
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
    }
}
