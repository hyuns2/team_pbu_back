package projectbuildup.mivv.domain.image;

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

    @Value("${path.root}")
    String ROOT_PATH;

    public ImageUploader(@Value("${path.root}") String property) {
        this.ROOT_PATH = property;
    }

    private static final String DELIMITER = "/";

    public ImageInfo upload(MultipartFile multipartFile) throws IOException {
        String originalName = Objects.requireNonNull(multipartFile.getOriginalFilename());
        String storeName = makeRandomName(originalName);
        String storePath = ROOT_PATH + DELIMITER + storeName;

        File file = new File(storePath);
        multipartFile.transferTo(file);
        return new ImageInfo(storeName, originalName, storePath);
    }

    public void delete(ImageInfo imageInfo) throws IOException {
        File file = new File(imageInfo.getPath());
        Files.deleteIfExists(Path.of(file.getAbsolutePath()));
    }

    private String makeRandomName(String fileName) {
        int index = fileName.lastIndexOf(".");
        String extension = fileName.substring(index + 1);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }


}
