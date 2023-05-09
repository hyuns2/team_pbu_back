package projectbuildup.mivv.domain.worthyConsumption.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "가치소비 영상 및 이미지 URL 요청 DTO")
public class WorthyConsumptionUrlDto {

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    @Schema(description = "가치소비 영상 및 이미지 URL 생성 DTO")
    public static class Creation {
        @NotNull
        String videoPath;
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
        String videoPath;
        private MultipartFile image;
        private MultipartFile detailImage;
        private MultipartFile detailBackgroundImage;
        private MultipartFile placeImage;

    }

}
