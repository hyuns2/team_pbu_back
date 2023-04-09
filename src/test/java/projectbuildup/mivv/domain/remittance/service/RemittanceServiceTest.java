package projectbuildup.mivv.domain.remittance.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import projectbuildup.mivv.domain.account.service.accountdetails.AccountDetailsSystem;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.remittance.dto.RemittanceDto;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@Slf4j
@SpringBootTest
class RemittanceServiceTest {

    @Autowired
    RemittanceService remittanceService;

    @Autowired
    RemittanceRepository remittanceRepository;

    @Test
    @DisplayName("송금이 이루어지지 않으면 절약금이 갱신되지 않는다.")
    void 송금이_이루어지지_않으면_절약금이_갱신되지_않는다() {
        // when
        RemittanceDto.RemitRequest requestDto = new RemittanceDto.RemitRequest(1L, 1L, 30000L);
        remittanceService.remit(requestDto, null);

        // then
        try {
            sleep(TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("송금이 이루어지면 절약금이 DB에 저장된다.")
    void 송금이_이루어지면_절약금이_DB에_저장된다() throws InterruptedException {
        // when
        LocalDateTime now = LocalDateTime.now();
        RemittanceDto.RemitRequest requestDto = new RemittanceDto.RemitRequest(1L, 1L, 300000L);
        remittanceService.remit(requestDto, LocalDateTime.of(2019,1,1,1,1,1));

        // then
        sleep(TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS));
        List<Remittance> remittances = remittanceRepository.findAllByCreatedTimeAfter(now);
        assertThat(remittances).isNotNull();
        assertThat(remittances.get(0).getAmount()).isEqualTo(300000L);
        sleep(TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS));
    }
}
