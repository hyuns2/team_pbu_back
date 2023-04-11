package projectbuildup.mivv.domain.worthyConsumption.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumptionUrl;

@Schema(description = "가치소비 영상 및 이미지 URL 요청 DTO")
public class WorthyConsumptionUrlRequestDto {
    @Builder
    @Getter
    @AllArgsConstructor @NoArgsConstructor
    @Schema(description = "가치소비 영상 및 이미지 URL 생성 요청 DTO")
    public static class CreationRequest {
        @NotBlank
        String videoUrl;
        @NotBlank
        String imageUrl;
        @NotBlank
        String detailImageUrl;
        @NotBlank
        String detailBackgroundImageUrl;
        @NotBlank
        String placeImageUrl;
        public WorthyConsumptionUrl toEntity(){
            return WorthyConsumptionUrl.builder()
                    .videoUrl(videoUrl)
                    .imageUrl(imageUrl)
                    .detailImageUrl(detailImageUrl)
                    .detailBackgroundImageUrl(detailBackgroundImageUrl)
                    .placeImageUrl(placeImageUrl)
                    .build();
        }
    }

}
