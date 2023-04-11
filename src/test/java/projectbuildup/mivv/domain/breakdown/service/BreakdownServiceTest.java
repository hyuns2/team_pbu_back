package projectbuildup.mivv.domain.breakdown.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import projectbuildup.mivv.domain.remittance.dto.RemittanceDto;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.remittance.service.RemittanceService;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
@Transactional
@Disabled
class BreakdownServiceTest {

    @Autowired
    RemittanceService remittanceService;

    @Autowired
    RemittanceRepository remittanceRepository;

    @Test
    @DisplayName("송금이 이루어지지 않으면 절약금이 갱신되지 않는다.")
    void 송금이_이루어지지_않으면_절약금이_갱신되지_않는다() throws InterruptedException, ExecutionException {
        // when
        RemittanceDto.RemitRequest requestDto = new RemittanceDto.RemitRequest(1L, 1L, 300000L);
        Future<Boolean> result = remittanceService.remit(requestDto, null);

        // then
        assertThat(result.get()).isFalse();
    }

    @Test
    @DisplayName("송금이 이루어지면 절약금이 DB에 저장된다.")
    void 송금이_이루어지면_절약금이_DB에_저장된다() throws InterruptedException, ExecutionException {
        // when
        RemittanceDto.RemitRequest requestDto = new RemittanceDto.RemitRequest(1L, 1L, 300000L);
        Future<Boolean> result = remittanceService.remit(requestDto, LocalDateTime.of(2019, 1, 1, 1, 1, 1));

        // then
        assertThat(result.get()).isTrue();
    }
}
