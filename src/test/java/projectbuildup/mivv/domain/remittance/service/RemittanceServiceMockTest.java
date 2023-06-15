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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import projectbuildup.mivv.domain.account.entity.TransactionDetail;
import projectbuildup.mivv.domain.account.service.accountdetails.AccountDetailsSystem;
import projectbuildup.mivv.domain.archiving.service.RemittanceArchivingService;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.challenge.service.RankScoreCalculator;
import projectbuildup.mivv.domain.challenge.service.RankingService;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.participation.repository.ParticipationRepository;
import projectbuildup.mivv.domain.remittance.dto.RemittanceDto;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CBadRequestException;
import projectbuildup.mivv.global.error.exception.CResourceNotFoundException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;
import projectbuildup.mivv.integrationtest.setting.MockEntityFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Slf4j
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class RemittanceServiceMockTest {

    @InjectMocks
    RemittanceService remittanceService;
    @Mock
    ChallengeRepository challengeRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ParticipationRepository participationRepository;
    @Mock
    RemittanceRepository remittanceRepository;
    @Mock
    RankingService rankingService;
    @Mock
    RankScoreCalculator rankScoreCalculator;
    @Mock
    RemittanceArchivingService remittanceArchivingService;

    @Mock
    AccountDetailsSystem accountDetailsSystem;

    @Mock
    AccountDetailsSystem testAccountDetailsSystem;

    @Test
    @DisplayName("참여했지만, 종료된 챌린지에는 송금할 수 없다.")
    void test() {
        // given
        User user = MockEntityFactory.mockUser(MockEntityFactory.mockIdentityVerification(), "테스트 유저");
        Challenge challenge = MockEntityFactory.mockChallenge(MockEntityFactory.mockImage(), "테스트 챌린지");
        Participation participation = new Participation(user, challenge);
        participation.close();
        given(userRepository.findById(any())).willReturn(Optional.of(user));
        given(challengeRepository.findById(any())).willReturn(Optional.of(challenge));
        given(participationRepository.findByChallengeAndUserAndClosedFalse(challenge, user)).willReturn(Optional.empty());

        // when

        // then
        RemittanceDto.RemitRequest requestDto = new RemittanceDto.RemitRequest(1L, 1L, LocalDate.now().atStartOfDay());
        Assertions.assertThatThrownBy(() -> remittanceService.checkSaving(requestDto)).isInstanceOf(CBadRequestException.class);
    }

    @Test
    @DisplayName("yyyyMM형태의 문자열을 시작~종료시간의 LocalDateTime으로 변환한다.")
    void test1() {
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