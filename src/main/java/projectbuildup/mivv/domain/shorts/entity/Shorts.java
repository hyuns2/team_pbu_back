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
    private String videoUrl;

    public Shorts(ShortsDto.creatRequest shortsRequestDto){
        this.title = shortsRequestDto.getTitle();
        this.content = shortsRequestDto.getContent();
        this.videoUrl = shortsRequestDto.getVideoUrl();
        this.category = shortsRequestDto.getCategory();
    }
    public void updateShorts(ShortsDto.updateRequest shortsRequestDto){
        this.title = shortsRequestDto.getTitle();
        this.content = shortsRequestDto.getContent();
        this.videoUrl = shortsRequestDto.getVideoUrl();
    }
}
