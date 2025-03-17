package projectbuildup.gasomann.global.error;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class ErrorResponseDtoTest {

    @Test
    @DisplayName("")
    void test() {
        // given
        String code = "CF-0000";

        // when
        String substring = code.substring("CF-".length());
        log.info(substring);

        // then
    }
}