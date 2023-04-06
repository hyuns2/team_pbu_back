package projectbuildup.mivv.domain.remittance.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RemittanceServiceTest {
    @Test
    @DisplayName("String -> DateTimeLocal 파싱 테스트")
    void 파싱_테스트() {
        // given
        LocalDateTime localDateTime = LocalDateTime.parse("19990311123456", DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // when

        // then
        assertThat(localDateTime).isEqualTo(LocalDateTime.of(1999, 3, 11, 12, 34, 56));
    }
}