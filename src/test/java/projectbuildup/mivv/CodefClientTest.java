package projectbuildup.mivv;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import projectbuildup.mivv.domain.account.service.accountsystem.CodefClient;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@Slf4j
@SpringBootTest
public class CodefClientTest {
    @Autowired
    CodefClient codefClient;
    static final String CONNECTED_ID = "byi1wYwD40k8hEIiXl6bRF";
    @Test
    @DisplayName("사용자의 입금 내역을 조회한다.")
    void 사용자의_입금_내역을_조회한다() {
        // given
        // when
        List<HashMap<String, String>> result = codefClient.getHistory(CONNECTED_ID, "0004", "3521395229353", LocalDate.of(2020, 6, 3));
        log.info("{}", result);
    }

}
