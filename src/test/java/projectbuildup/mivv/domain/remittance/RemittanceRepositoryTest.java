package projectbuildup.mivv.domain.remittance;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import projectbuildup.mivv.common.MockEntity;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.config.JpaAuditingConfig;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Import(JpaAuditingConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class RemittanceRepositoryTest {
    @Autowired
    RemittanceRepository remittanceRepository;
    @Autowired
    ChallengeRepository challengeRepository;
    @Autowired
    UserRepository userRepository;

    @MockBean
    private DateTimeProvider dateTimeProvider;
    @SpyBean
    private AuditingHandler handler;

    @Test
    @DisplayName("해당 유저의 모든 절약 내역을 조회한다.")
    void test1() {
        // given
        handler.setDateTimeProvider(dateTimeProvider);
        LocalDateTime createdDateTime = LocalDateTime.of(2023, 4, 6, 1, 1, 1);

        given(dateTimeProvider.getNow()).willReturn(Optional.of(createdDateTime));
        User user = MockEntity.MOCK_USER;
        userRepository.save(user);
        Challenge challenge = MockEntity.MOCK_CHALLENGE;
        challengeRepository.save(challenge);
        Remittance remittance = Remittance.builder().user(user).challenge(challenge).amount(10000L).build();
        Remittance saved = remittanceRepository.save(remittance);
        LocalDateTime startDate = LocalDate.of(2022, 4, 5).atStartOfDay();
        LocalDateTime endDate = LocalDate.of(2030, 4, 7).atTime(LocalTime.MAX);

        // when
        List<Remittance> result = remittanceRepository.findAllByUserAndCreatedTimeBetween(user, startDate, endDate);

        // then
        log.info("saved_time:{}", saved.getCreatedTime());
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(saved.getId());
    }
}