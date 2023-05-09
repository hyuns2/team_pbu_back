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
    private String title;
    private String content;
    private String imagePath;
    private String videoPath;

    public Shorts(ShortsDto.creatRequest shortsRequestDto, String imagePath){
        this.title = shortsRequestDto.getTitle();
        this.content = shortsRequestDto.getContent();
        this.imagePath = imagePath;
        this.videoPath = shortsRequestDto.getVideoPath();
        this.category = shortsRequestDto.getCategory();
    }
    public void update(ShortsDto.updateRequest shortsRequestDto, String imagePath){
        this.title = shortsRequestDto.getTitle();
        this.content = shortsRequestDto.getContent();
        this.imagePath = imagePath;
        this.videoPath = shortsRequestDto.getVideoPath();
    }
}
