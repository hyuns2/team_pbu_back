package projectbuildup.mivv.global.common.videoStore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.global.common.imageStore.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class VideoUploader {
    @Value("${path.videos}")
    String STORE_PATH;

    @Value("{path.ipUrl}")
    String ipUrl;

    private static final String DELIMITER = "/";

    public Video upload(MultipartFile multipartFile, String dirName) throws IOException {
        String originalName = Objects.requireNonNull(multipartFile.getOriginalFilename());
        String storeName = makeRandomName(originalName);
        String storePath = STORE_PATH + DELIMITER + dirName + DELIMITER + storeName;
        File file = new File(storePath);
        multipartFile.transferTo(file);
        return new Video(storeName, originalName, ipUrl + storePath);
    }

    public void delete(Video video) throws IOException {
        File file = new File(video.getVideoPath());
        Files.deleteIfExists(Path.of(file.getAbsolutePath()));
    }

    private String makeRandomName(String fileName) {
        int index = fileName.lastIndexOf(".");
        String extension = fileName.substring(index + 1);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }
}
