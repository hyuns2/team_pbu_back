package projectbuildup.mivv.domain.shorts.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import projectbuildup.mivv.domain.shorts.dto.ShortsDto;

@Entity
@Slf4j
@AllArgsConstructor @NoArgsConstructor(force = true)
//@RequiredArgsConstructor
@Getter @Builder
public class Shorts {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ShortsCategory category;
    @Setter
    private String title;
    @Setter
    private String content;
    @Setter
    private String imagePath;
    @Setter
    private String videoPath;

    public Shorts(ShortsDto.creatRequest shortsDto, String imagePath){
        this.title = shortsDto.getTitle();
        this.content = shortsDto.getContent();
        this.imagePath = imagePath;
        this.videoPath = shortsDto.getVideoPath();
        this.category = shortsDto.getCategory();
    }
    public void update(ShortsDto.updateRequest shortsRequestDto){
        if(shortsRequestDto.getTitle()!=null||shortsRequestDto.getTitle()!=""|| !shortsRequestDto.getTitle().isEmpty())
            this.title = shortsRequestDto.getTitle();
        if(shortsRequestDto.getContent()!=null||shortsRequestDto.getContent()!=""|| !shortsRequestDto.getContent().isEmpty())
            this.content = shortsRequestDto.getContent();
        if(shortsRequestDto.getVideoPath() != null || shortsRequestDto.getVideoPath()!="")
            this.videoPath = shortsRequestDto.getVideoPath();
    }
    public void updateImage(String imagePath){
        this.imagePath = imagePath;
    }
}
