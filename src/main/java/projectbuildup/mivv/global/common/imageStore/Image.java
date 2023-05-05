package projectbuildup.mivv.global.common.imageStore;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Image {
    String originalImageName;
    String storeImageName;
    String imagePath;
}
