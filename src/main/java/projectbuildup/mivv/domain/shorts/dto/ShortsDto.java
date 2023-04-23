package projectbuildup.mivv.domain.shorts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectbuildup.mivv.domain.shorts.entity.Shorts;
import projectbuildup.mivv.domain.shorts.entity.ShortsType;
import projectbuildup.mivv.domain.shorts.service.ShortsService;

public class ShortsDto {
    @Getter @AllArgsConstructor @NoArgsConstructor
    public static class idRequest{
        @Setter
        Long id;
    }
    @Getter
    public static class creatRequest{
        @NotBlank(message = "제목은 필수 입력값입니다.")
        String title;
        @NotBlank(message = "설명은 필수 입력값입니다.")
        String content;
        @NotBlank(message = "영상 주소는 필수 입력값입니다.")
        String videoUrl;
        @NotNull(message = "영상 타입은 필수 입력값입니다.")
        ShortsType type;
    }
    @Getter
    public class updateRequest extends idRequest{
        String title;
        String content;
        String videoUrl;
    }
    @Getter
    public static class shortsResponse extends ShortsDto.creatRequest{
        public shortsResponse(Shorts shorts){
            this.title = shorts.getTitle();
            this.content = shorts.getContent();
            this.videoUrl = shorts.getVideoUrl();
            this.type = shorts.getType();
        }
    }

}
