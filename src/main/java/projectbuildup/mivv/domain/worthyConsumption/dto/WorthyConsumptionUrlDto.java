package projectbuildup.mivv.domain.worthyConsumption.dto;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "가치소비 영상 및 이미지 URL 요청 DTO")
public class WorthyConsumptionUrlDto {

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    @Schema(description = "가치소비 영상 및 이미지 URL 생성 DTO")
    public static class Creation {
        @NotNull
        private MultipartFile video;
        @NotNull
        private MultipartFile image;
        @NotNull
        private MultipartFile detailImage;
        @NotNull
        private MultipartFile detailBackgroundImage;
        @NotNull
        private MultipartFile placeImage;

    }
    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    @Schema(description = "가치소비 영상 및 이미지 URL 수정 DTO")
    public static class Update {
        @NotNull
        private MultipartFile video;
        @NotNull
        private MultipartFile image;
        @NotNull
        private MultipartFile detailImage;
        @NotNull
        private MultipartFile detailBackgroundImage;
        @NotNull
        private MultipartFile placeImage;

    }

}
