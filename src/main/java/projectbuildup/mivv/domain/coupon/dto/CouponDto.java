package projectbuildup.mivv.domain.coupon.dto;

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
        @NotBlank(message = "쿠폰의 이름을 입력해주세요")
        private String title;
        private MultipartFile image;
        @NotNull(message = "쿠폰의 핀 번호를 입력해주세요")
        private Integer pin;
        @NotNull @PastOrPresent
        private LocalDate limitStartDate;
        @NotNull @Future
        private LocalDate limitEndDate;
    }
    @AllArgsConstructor @Data
    @Schema(description = "쿠폰 수정 DTO")
    public class Update{
        private String title;
        private MultipartFile image;
        private Integer pin;
        private LocalDate limitStartDate;
        private LocalDate limitEndDate;
    }
}
