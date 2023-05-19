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

    public Shorts(ShortsDto.creatRequest shortsDto, String videoPath, String imagePath){
        this.title = shortsDto.getTitle();
        this.content = shortsDto.getContent();
        this.imagePath = imagePath;
        this.videoPath = videoPath;
        this.category = shortsDto.getCategory();
    }
    public void update(ShortsDto.updateRequest shortsDto, String imagePath, String videoPath){
        this.title = shortsDto.getTitle();
        this.content = shortsDto.getContent();
        this.imagePath = imagePath;
        this.videoPath = videoPath;
        this.category = shortsDto.getCategory();

    }
    public void updateImage(String imagePath){
        this.imagePath = imagePath;
    }
    public void updateVideo(String videoPath){
        this.videoPath = videoPath;
    }
}
