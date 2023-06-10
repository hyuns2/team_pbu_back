package projectbuildup.mivv.global.common.imageStore;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Image {
    String originalImageName;
    String storeImageName;
    String imagePath;

    public static Image newDefaultProfileImage() {
        return new Image("default_user_profile.png", "default_user_profile.png", "https://server.gasomann.com/resources/static/images/profiles/default_user_profile.png");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Image image = (Image) o;
        return originalImageName.equals(image.originalImageName) && storeImageName.equals(image.storeImageName) && imagePath.equals(image.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalImageName, storeImageName, imagePath);
    }
}
