package projectbuildup.mivv.global.common.imageStore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.global.error.exception.CBadRequestException;
import projectbuildup.mivv.global.error.exception.CInternalServerException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.UUID;

@Slf4j
@Component
public class ImageUploader {

    @Value("${path.images}")
    String STORE_PATH;

    @Value("${path.ipUrl}")
    String IP_ADDRESS;

    private static final String DELIMITER = "/";

    /**
     * 이미지 파일을 업로드합니다.
     * STORE_PATH는 이미지가 저장될 상위 디렉토리를 지정합니다.
     * 저장되는 파일의 이름은 UUID 랜덤 문자열입니다.
     *
     * @param multipartFile 멀티파트파일
     * @param imageType     이미지가 저장될 하위 디렉토리 (이미지 종류에 따라 분류)
     * @return 이미지 메타 정보
     */
    public Image upload(MultipartFile multipartFile, ImageType imageType) {
        validate(multipartFile);
        try {
            multipartFile.getName();
            String originalName = Normalizer.normalize(multipartFile.getOriginalFilename(), Normalizer.Form.NFC);
            String storeName = makeRandomName(originalName);
            String storePath = STORE_PATH + DELIMITER + imageType.getDirectoryName() + DELIMITER + storeName;
            File file = new File(storePath);
            multipartFile.transferTo(file);
            return new Image(storeName, originalName, IP_ADDRESS + storePath);
        } catch (IOException e) {
            log.error("파일 업로드 중 오류 발생 !");
            throw new CInternalServerException();
        }
    }

    private void validate(MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new CBadRequestException("이미지 파일이 비어있습니다.");
        }
        if (multipartFile.getOriginalFilename() == null) {
            throw new CBadRequestException("파일명이 없습니다.");
        }
    }

    private String makeRandomName(String fileName) {
        int index = fileName.lastIndexOf(".");
        String extension = fileName.substring(index + 1);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }

    /**
     * 저장된 이미지 파일을 삭제합니다.
     *
     * @param image 삭제할 이미지 정보
     * @throws IOException
     */
    public boolean deleteIfExists(Image image) throws IOException {
        File file = new File(getRelativePath(image));
        return Files.deleteIfExists(Path.of(file.getAbsolutePath()));
    }

    private String getRelativePath(Image image) {
        String fullPath = image.getImagePath();
        return fullPath.substring(IP_ADDRESS.length());
    }
}
