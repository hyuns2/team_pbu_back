package projectbuildup.mivv.global.common.imageStore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class ImageUploader {

    @Value("${path.images}")
    String STORE_PATH;

    public ImageUploader(@Value("${path.images}") String property) {
        this.STORE_PATH = property;
    }

    private static final String DELIMITER = "/";

    public Image upload(MultipartFile multipartFile, String dirName) throws IOException {
        String originalName = Objects.requireNonNull(multipartFile.getOriginalFilename());
        String storeName = makeRandomName(originalName);
        String storePath = STORE_PATH + DELIMITER + dirName + DELIMITER + storeName;

        File file = new File(storePath);
        multipartFile.transferTo(file);
        return new Image(storeName, originalName, storePath);
    }

    public void delete(Image image) throws IOException {
        File file = new File(image.getImagePath());
        Files.deleteIfExists(Path.of(file.getAbsolutePath()));
    }

    private String makeRandomName(String fileName) {
        int index = fileName.lastIndexOf(".");
        String extension = fileName.substring(index + 1);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }
}
