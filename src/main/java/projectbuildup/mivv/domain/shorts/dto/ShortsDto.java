package projectbuildup.mivv.domain.shorts.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.shorts.entity.Shorts;
import projectbuildup.mivv.domain.shorts.entity.ShortsCategory;

public class ShortsDto {
    @Getter @Setter
    @AllArgsConstructor
    public static class creatRequest{
        @NotBlank(message = "제목은 필수 입력값입니다.")
        String uploaderName;
        @NotBlank(message = "설명은 필수 입력값입니다.")
        String content;
        //@NotBlank(message = "영상 주소는 필수 입력값입니다.")
        //String videoPath;
        private MultipartFile video;
        @NotNull(message = "영상의 썸네일 이미지는 필수 입력값입니다.")
        private MultipartFile image;
        @NotNull(message = "영상 카테고리는 필수 입력값입니다.") @Enumerated(EnumType.STRING)
        ShortsCategory category;
    }
    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class updateRequest {
        String uploaderName;
        String content;
        //String videoPath;
        private MultipartFile video;
        private MultipartFile image;
        ShortsCategory category;
    }
    @Data
    @AllArgsConstructor
    public static class shortsResponse {
        private Long id;
        String uploaderName;
        String content;
        String videoPath;
        String imagePath;
        @Enumerated(EnumType.STRING)
        ShortsCategory category;
        Boolean liked;
    public shortsResponse(Shorts shorts, Boolean liked){
        this.id = shorts.getId();
        this.uploaderName = shorts.getUploaderName();
        this.content = shorts.getContent();
        this.videoPath = shorts.getVideoPath();
        this.imagePath= shorts.getImagePath();
        this.category = shorts.getCategory();
        this.liked = liked;
        }
    }
}
