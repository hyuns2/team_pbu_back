package projectbuildup.mivv.global.error;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.annotation.Scheduled;

import static org.junit.jupiter.api.Assertions.*;

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