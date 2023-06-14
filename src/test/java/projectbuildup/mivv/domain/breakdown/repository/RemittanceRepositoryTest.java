package projectbuildup.mivv.domain.breakdown.repository;

import lombok.extern.slf4j.Slf4j;
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
import org.springframework.test.context.ActiveProfiles;
import projectbuildup.mivv.domain.auth.repository.IdentityVerificationRepository;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.participation.repository.ParticipationRepository;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.common.BaseTimeEntity;
import projectbuildup.mivv.global.config.JpaAuditingConfig;
import projectbuildup.mivv.integrationtest.setting.MockEntityFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;
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
    @Autowired
    ParticipationRepository participationRepository;
    @Autowired
    IdentityVerificationRepository identityVerificationRepository;
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
        IdentityVerification iv = MockEntityFactory.mockIdentityVerification();
        identityVerificationRepository.save(iv);
        User user = MockEntityFactory.mockUser(iv, "유저1");        userRepository.save(user);
        Challenge challenge = MockEntityFactory.mockChallenge(MockEntityFactory.mockImage(), "챌린지");
        challengeRepository.save(challenge);
        Participation participation = new Participation(user, challenge);
        participationRepository.save(participation);
        Remittance remittance = new Remittance(participation, 10000L);
        Remittance saved = remittanceRepository.save(remittance);

        LocalDateTime startDate = LocalDate.of(2022, 4, 5).atStartOfDay();
        LocalDateTime endDate = LocalDate.of(2030, 4, 7).atTime(LocalTime.MAX);

        // when
        List<Remittance> result = remittanceRepository.findByUserAndYearMonth(user, startDate, endDate);

        // then
        log.info("saved_time:{}", saved.getCreatedTime());
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(saved.getId());
    }

    @Test
    @DisplayName("사용자의 총 절약 금액을 조회한다.")
    void test2() {
        // given
        IdentityVerification iv = MockEntityFactory.mockIdentityVerification();
        identityVerificationRepository.save(iv);
        User user = MockEntityFactory.mockUser(iv, "유저1");        userRepository.save(user);
        Challenge challenge = MockEntityFactory.mockChallenge(MockEntityFactory.mockImage(), "챌린지");
        Challenge saved = challengeRepository.save(challenge);
        log.info("{}", saved);
        List<Challenge> found = challengeRepository.findAll();
        log.info("{}", found);
        Participation participation = new Participation(user, challenge);
        participationRepository.save(participation);
        Remittance remittance1 = new Remittance(participation, 1000L);
        Remittance remittance2 = new Remittance(participation, 2000L);
        Remittance remittance3 = new Remittance(participation, -500L);
        Remittance remittance4 = new Remittance(participation, 3000L);
        remittanceRepository.save(remittance1);
        remittanceRepository.save(remittance2);
        remittanceRepository.save(remittance3);
        remittanceRepository.save(remittance4);

        given(remittanceRepository.findSumAmountByUser(user)).willReturn(5500L);

        // when
        long result = remittanceRepository.findSumAmountByUser(user);

        // then
        assertThat(result).isEqualTo(5500L);
    }

    @Test
    @DisplayName("현재 달에 절약한 금액의 총합을 조회한다.")
    void test3() {
        // given
        IdentityVerification iv = MockEntityFactory.mockIdentityVerification();
        identityVerificationRepository.save(iv);
        User user = MockEntityFactory.mockUser(iv, "유저1");
        userRepository.save(user);
        Challenge challenge = MockEntityFactory.mockChallenge(MockEntityFactory.mockImage(), "챌린지1");
        challengeRepository.save(challenge);
        Participation participation = new Participation(user, challenge);
        participationRepository.save(participation);
        Remittance remittance1 = new Remittance(participation, 1000L);
        Remittance remittance2 = new Remittance(participation, 2000L);
        Remittance remittance3 = new Remittance(participation, 3000L);
        Remittance remittance4 = new Remittance(participation, 4000L);
        remittanceRepository.saveAll(List.of(remittance1, remittance2, remittance3, remittance4));

        // when
        YearMonth yearMonth = YearMonth.now();
        LocalDateTime startTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endTime = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);
        Long result = remittanceRepository.findSumAmountByUserAndCreatedTimeBetween(user, startTime, endTime);

        // then
        assertThat(result).isEqualTo(10000L);
    }

    @Test
    @DisplayName("현재 달에 절약한 금액이 없을 경우 0을 반환한다.")
    void test4() {
        // given
        IdentityVerification iv = MockEntityFactory.mockIdentityVerification();
        identityVerificationRepository.save(iv);
        User user = MockEntityFactory.mockUser(iv, "유저1");        userRepository.save(user);
        Challenge challenge = MockEntityFactory.mockChallenge(MockEntityFactory.mockImage(), "챌린지1");
        challengeRepository.save(challenge);
        Participation participation = new Participation(user, challenge);
        participationRepository.save(participation);

        // when
        YearMonth yearMonth = YearMonth.now();
        LocalDateTime startTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endTime = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);
        Long result = remittanceRepository.findSumAmountByUserAndCreatedTimeBetween(user, startTime, endTime);

        // then
        assertThat(result).isEqualTo(0L);
    }
}