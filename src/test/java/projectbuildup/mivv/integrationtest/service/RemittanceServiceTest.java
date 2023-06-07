package projectbuildup.mivv.integrationtest.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import projectbuildup.mivv.domain.remittance.dto.RemittanceDto;
import projectbuildup.mivv.domain.remittance.service.RemittanceService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CBadRequestException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;
import projectbuildup.mivv.integrationtest.setting.IntegrationTest;
import projectbuildup.mivv.integrationtest.setting.WithAuthUser;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

    @Test
    @DisplayName("절약금이 확인된 경우, 갱신한다.")
    void test1() {
        // given
        RemittanceDto.RemitRequest requestDto = new RemittanceDto.RemitRequest(1L, 1L);

        // when
        boolean result = remittanceService.checkSavingForTest(requestDto, Optional.empty());

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("절약금을 확인하지 못하면 갱신하지 않는다.")
    void test2() {
        // given
        RemittanceDto.RemitRequest requestDto = new RemittanceDto.RemitRequest(1L, 1L);

        // when

        // then
        assertThatThrownBy(() -> remittanceService.checkSavingForTest(requestDto, Optional.of(LocalDateTime.of(2019, 5, 11, 4, 0, 0))))
                .isInstanceOf(CBadRequestException.class);
    }
}
