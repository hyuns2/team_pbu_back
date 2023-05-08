package projectbuildup.mivv.domain.worthyConsumption.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumptionUrl;

@Schema(description = "가치소비 영상 및 이미지 URL 요청 DTO")
public class WorthyConsumptionUrlDto {

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    @Schema(description = "가치소비 영상 및 이미지 URL 생성 요청 DTO")
    public static class CreationRequest {
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

}
