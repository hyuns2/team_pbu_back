package projectbuildup.mivv.domain.shorts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import projectbuildup.mivv.domain.shorts.entity.Shorts;
import projectbuildup.mivv.domain.shorts.entity.ShortsType;
import projectbuildup.mivv.domain.shorts.service.ShortsService;

public class ShortsDto {
    @Getter
    public class idRequest{
        Long id;
    }
    @Getter
    public static class creatRequest{
        String title;
        String content;
        String videoUrl;
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
