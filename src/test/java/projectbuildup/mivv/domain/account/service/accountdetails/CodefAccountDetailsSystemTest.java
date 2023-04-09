package projectbuildup.mivv.domain.account.service.accountdetails;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;

import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
class CodefAccountDetailsSystemTest {
    @Autowired
    CodefAccountDetailsSystem codefAccountDetailsSystem;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("계좌 내역을 조회한다.")
    void 계좌_내역을_조회한다() {
        // given
        User user = userRepository.findById(1L).get();
        // when
        List<Map<String, String>> history = codefAccountDetailsSystem.getDepositHistory(user);
        log.info("{}", history);
        // then
    }
}