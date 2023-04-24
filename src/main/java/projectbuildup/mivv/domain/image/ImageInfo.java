package projectbuildup.mivv.domain.image;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ImageInfo {
    String storeFileName;
    String originalFileName;
    String path;
}
