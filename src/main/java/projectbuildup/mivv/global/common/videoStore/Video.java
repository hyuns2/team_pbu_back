package projectbuildup.mivv.global.common.videoStore;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Video {
    String originalVideoName;
    String storeVideoName;
    String videoPath;
}
