package projectbuildup.mivv.domain.shorts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import projectbuildup.mivv.domain.shorts.dto.ShortsDto;

@Entity
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
public class Shorts {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ShortsType type;
    private String title;
    private String content;
    private String videoUrl;

    public Shorts(ShortsDto.creatRequest shortsRequestDto){
        this.title = shortsRequestDto.getTitle();
        this.content = shortsRequestDto.getContent();
        this.videoUrl = shortsRequestDto.getVideoUrl();
        this.type = shortsRequestDto.getType();
    }
    public void updateShorts(ShortsDto.updateRequest shortsRequestDto){
        this.title = shortsRequestDto.getTitle();
        this.content = shortsRequestDto.getContent();
        this.videoUrl = shortsRequestDto.getVideoUrl();
    }
}
