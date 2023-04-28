package projectbuildup.mivv.domain.shorts.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectbuildup.mivv.domain.shorts.entity.Shorts;
import projectbuildup.mivv.domain.shorts.entity.ShortsCategory;

public class ShortsDto {
    @Getter @AllArgsConstructor @NoArgsConstructor
    public static class idRequest{
        @Setter
        Long id;
    }
    @Getter @Setter
    public static class creatRequest{
        @NotBlank(message = "제목은 필수 입력값입니다.")
        String title;
        @NotBlank(message = "설명은 필수 입력값입니다.")
        String content;
        @NotBlank(message = "영상 주소는 필수 입력값입니다.")
        String videoUrl;
        @NotBlank(message = "영상의 썸네일 이미지는 필수 입력값입니다.")
        String imageUrl;
        @NotNull(message = "영상 카테고리는 필수 입력값입니다.") @Enumerated(EnumType.STRING)
        ShortsCategory category;
    }
    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class updateRequest extends idRequest{
        String title;
        String content;
        String videoUrl;
        String imageUrl;
    }
    @Getter
    public static class shortsResponse extends ShortsDto.creatRequest{
        public shortsResponse(Shorts shorts){
            this.title = shorts.getTitle();
            this.content = shorts.getContent();
            this.videoUrl = shorts.getVideoUrl();
            this.imageUrl = shorts.getImageUrl();
            this.category = shorts.getCategory();
        }
    }

}
