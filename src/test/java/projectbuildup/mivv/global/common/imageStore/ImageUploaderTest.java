package projectbuildup.mivv.global.common.imageStore;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ImageUploaderTest {
    @Test
    @DisplayName("전체 이미지 경로에서 서버 주소(prefix)를 제외한 부분을 구한다.")
    void test() {
        // given
        String SERVER_ADDRESS = "https://server.gasomann.com";
        String imageUrl = "https://server.gasomann.com/resources/static/images/profiles/default_user_profile.png";

        //when
        String result = imageUrl.substring(SERVER_ADDRESS.length());

        // then
        assertThat(result).isEqualTo("/resources/static/images/profiles/default_user_profile.png");

    }

    @Test
    @DisplayName("서버 주소(prefix)가 공백인 경우 전체 이미지 경로를 리턴한다.")
    void test1() {
        // given
        String SERVER_ADDRESS = "";
        String imageUrl = "E:\\ROOM\\Github\\mivv-backend\\src\\test\\resources\\store\\images/profiles/f17faf5c-6782-467c-b5b4-1865855969a7.png";

        //when
        String result = imageUrl.substring(SERVER_ADDRESS.length());

        // then
        assertThat(result).isEqualTo(imageUrl);
    }
}