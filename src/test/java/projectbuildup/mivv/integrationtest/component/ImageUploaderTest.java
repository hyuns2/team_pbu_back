package projectbuildup.mivv.integrationtest.component;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import projectbuildup.mivv.global.common.imageStore.Image;
import projectbuildup.mivv.global.common.imageStore.ImageType;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
import projectbuildup.mivv.global.error.exception.CBadRequestException;
import projectbuildup.mivv.integrationtest.setting.IntegrationTest;

import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class ImageUploaderTest extends IntegrationTest {

    @Autowired
    ImageUploader imageUploader;

    private MockMultipartFile getMockMultipartFile(String fileName, String contentType, String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        return new MockMultipartFile(fileName, fileName + "." + contentType, contentType, fileInputStream);
    }


    @Test
    @DisplayName("ImageUploader를 이용해 이미지 파일을 저장하고, 삭제한다.")
    void test1() throws IOException {
        // given
        String FILE_NAME = "mock-image1";
        String CONTENT_TYPE = "jpg";
        String PATH = "src/test/java/projectbuildup/mivv/integrationtest/setting/files/mock-image1.jpg";
        MockMultipartFile mockMultipartFile = getMockMultipartFile(FILE_NAME, CONTENT_TYPE, PATH);

        // when
        Image image = imageUploader.upload(mockMultipartFile, ImageType.USER_PROFILE);

        // then
        assertThat(imageUploader.deleteIfExists(image)).isTrue();
    }

    @Test
    @DisplayName("MultipartFile.getName()은 확장자를 제외한 파일명을 반환하고, MultipartFile.getOriginalFilename()은 확장자를 포함한 파일명을 반환한다.")
    void test2() throws IOException {
        // given
        String FILE_NAME = "mock-image1";
        String CONTENT_TYPE = "jpg";
        String PATH = "src/test/java/projectbuildup/mivv/integrationtest/setting/files/mock-image1.jpg";
        MockMultipartFile mockMultipartFile = getMockMultipartFile(FILE_NAME, CONTENT_TYPE, PATH);

        // when

        // then
        assertThat(mockMultipartFile.getName()).isEqualTo("mock-image1");
        assertThat(mockMultipartFile.getOriginalFilename()).isEqualTo("mock-image1.jpg");
    }

    @Test
    @DisplayName("빈 MultipartFile을 업로드 시도하는 경우 예외가 발생한다. ")
    void test3()  {
        // given

        // when

        // then
        assertThatThrownBy(() -> imageUploader.upload(null, ImageType.USER_PROFILE)).isInstanceOf(CBadRequestException.class);
    }

    @Test
    @DisplayName("삭제하려는 파일이 없는 경우 삭제하지 않는다.")
    void test4() throws IOException {
        // given
        Image emptyImage = new Image("empty", "empty", "path");

        // when
        boolean result = imageUploader.deleteIfExists(emptyImage);

        // then
        assertThat(result).isFalse();
    }
}
