package projectbuildup.mivv.domain.remittance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class RemittanceServiceMockTest {

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
