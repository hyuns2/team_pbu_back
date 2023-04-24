package projectbuildup.mivv.domain.image;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImageUploaderTest {

    private MockMultipartFile getMockMultipartFile(String fileName, String contentType, String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        return new MockMultipartFile(fileName, fileName + "." + contentType, contentType, fileInputStream);
    }

    @Test
    @DisplayName("ImageUploader로 파일을 저장한다.")
    void test1() throws IOException {
        // given
        String FILE_NAME = "test01";
        String CONTENT_TYPE = "jpg";
        String IMAGE_PATH = "src/test/resources/test01.jpg";
        String STORE_PATH = "E:/ROOM/Github/mivv-backend/src/test/resources/store/images";

        MockMultipartFile mockMultipartFile = getMockMultipartFile(FILE_NAME, CONTENT_TYPE, IMAGE_PATH);
        ImageUploader imageUploader = new ImageUploader(STORE_PATH);

        // when
        ImageInfo imageInfo = imageUploader.upload(mockMultipartFile);

        // then
        File file = new File(imageInfo.getPath());
        assertTrue(file.exists());
        imageUploader.delete(imageInfo);
    }

    @Test
    @DisplayName("ImageUploader로 파일을 삭제한다.")
    void test2() throws IOException {
        // given
        String FILE_NAME = "test01";
        String CONTENT_TYPE = "jpg";
        String IMAGE_PATH = "src/test/resources/test01.jpg";
        String STORE_PATH = "E:/ROOM/Github/mivv-backend/src/test/resources/store";

        MockMultipartFile mockMultipartFile = getMockMultipartFile(FILE_NAME, CONTENT_TYPE, IMAGE_PATH);
        ImageUploader imageUploader = new ImageUploader(STORE_PATH);
        ImageInfo imageInfo = imageUploader.upload(mockMultipartFile);

        // when
        imageUploader.delete(imageInfo);

        // then
        File file = new File(imageInfo.getPath());
        assertFalse(file.exists());
    }
}