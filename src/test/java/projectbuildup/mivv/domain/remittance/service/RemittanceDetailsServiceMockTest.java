package projectbuildup.mivv.domain.remittance.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class RemittanceDetailsServiceMockTest {

    @Test
    @DisplayName("yyyyMM형태의 문자열을 시작~종료시간의 LocalDateTime으로 변환한다.")
    void test() {
        // given
        String str = "202303";

        // when
        LocalDate localDate = LocalDate.parse(str + "01", DateTimeFormatter.ofPattern("yyyyMMdd"));
        YearMonth yearMonth = YearMonth.from(localDate);
        LocalDateTime startTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endTime = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);

        // then
        log.info("{}", startTime);
        log.info("{}", endTime);
        assertThat(startTime).isEqualTo(LocalDate.of(2023, 3, 1).atStartOfDay());
        assertThat(endTime).isEqualTo(LocalDate.of(2023, 3, 31).atTime(LocalTime.MAX));
    }

}