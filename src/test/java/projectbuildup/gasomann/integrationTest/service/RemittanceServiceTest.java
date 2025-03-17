package projectbuildup.gasomann.integrationTest.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import projectbuildup.gasomann.domain.remittance.service.RemittanceService;
import projectbuildup.gasomann.domain.user.entity.User;
import projectbuildup.gasomann.domain.user.repository.UserRepository;
import projectbuildup.gasomann.global.error.exception.CUserNotFoundException;
import projectbuildup.gasomann.integrationTest.setting.IntegrationTest;
import projectbuildup.gasomann.integrationTest.setting.WithAuthUser;

import java.time.YearMonth;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RemittanceServiceTest extends IntegrationTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RemittanceService remittanceService;

    @Test
    @WithAuthUser(role = "USER", id = "1")
    @DisplayName("이전 달(2023-05)의 총 절약 금액을 조회한다.")
    void test() {
        // given
        User user = userRepository.findById(1L).orElseThrow(CUserNotFoundException::new);

        // when
        long result = remittanceService.getPreviousSavingAmount(user.getId(), Optional.of(YearMonth.of(2023, 5)));

        // then
        assertThat(result).isEqualTo(12000);
    }
}
