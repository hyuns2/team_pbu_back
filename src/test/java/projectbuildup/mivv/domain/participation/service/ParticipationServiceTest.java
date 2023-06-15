package projectbuildup.mivv.domain.participation.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.challenge.service.RankingService;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.participation.repository.ParticipationRepository;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.remittance.service.RemittanceService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CBadRequestException;
import projectbuildup.mivv.global.error.exception.CResourceNotFoundException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;
import projectbuildup.mivv.integrationtest.setting.MockEntityFactory;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ParticipationServiceTest {
    @InjectMocks
    ParticipationService participationService;

    @Mock
    UserRepository userRepository;
    @Mock
    ChallengeRepository challengeRepository;
    @Mock
    ParticipationRepository participationRepository;
    @Mock
    RankingService rankingService;

    @Test
    @DisplayName("진행 중인 챌린지에 참여한다.")
    void test() {
        // given
        User user = MockEntityFactory.mockUser(MockEntityFactory.mockIdentityVerification(), "테스트 유저");
        Challenge challenge = MockEntityFactory.mockChallenge(MockEntityFactory.mockImage(), "테스트 챌린지");
        given(userRepository.findById(any())).willReturn(Optional.of(user));
        given(challengeRepository.findById(any())).willReturn(Optional.of(challenge));
        given(participationRepository.findByChallengeAndUser(challenge, user)).willReturn(Optional.empty());

        // when
        participationService.joinChallenge(1L, 1L);

        // then
    }

    @Test
    @DisplayName("참여 중인 챌린지에는 참여할 수 없다.")
    void test1() {
        // given
        User user = MockEntityFactory.mockUser(MockEntityFactory.mockIdentityVerification(), "테스트 유저");
        Challenge challenge = MockEntityFactory.mockChallenge(MockEntityFactory.mockImage(), "테스트 챌린지");
        Participation participation = new Participation(user, challenge);
        given(userRepository.findById(any())).willReturn(Optional.of(user));
        given(challengeRepository.findById(any())).willReturn(Optional.of(challenge));
        given(participationRepository.findByChallengeAndUser(challenge, user)).willReturn(Optional.of(participation));

        // when

        // then
        Assertions.assertThatThrownBy(() -> participationService.joinChallenge(1L, 1L)).isInstanceOf(CBadRequestException.class);
    }

    @Test
    @DisplayName("종료된 챌린지에는 참여할 수 없다.")
    void test2() {
        // given
        User user = MockEntityFactory.mockUser(MockEntityFactory.mockIdentityVerification(), "테스트 유저");
        Challenge challenge = MockEntityFactory.mockChallenge(MockEntityFactory.mockImage(), "테스트 챌린지");
        Participation participation = new Participation(user, challenge);
        participation.close();
        given(userRepository.findById(any())).willReturn(Optional.of(user));
        given(challengeRepository.findById(any())).willReturn(Optional.of(challenge));
        given(participationRepository.findByChallengeAndUser(challenge, user)).willReturn(Optional.of(participation));

        // when

        // then
        Assertions.assertThatThrownBy(() -> participationService.joinChallenge(1L, 1L)).isInstanceOf(CBadRequestException.class);
    }

    @Test
    @DisplayName("진행 중인 챌린지를 포기한다.")
    void test3() {
        // given
        User user = MockEntityFactory.mockUser(MockEntityFactory.mockIdentityVerification(), "테스트 유저");
        Challenge challenge = MockEntityFactory.mockChallenge(MockEntityFactory.mockImage(), "테스트 챌린지");
        Participation participation = new Participation(user, challenge);
        given(userRepository.findById(any())).willReturn(Optional.of(user));
        given(challengeRepository.findById(any())).willReturn(Optional.of(challenge));
        given(participationRepository.findByChallengeAndUserAndClosedFalse(challenge, user)).willReturn(Optional.of(participation));

        // when
        participationService.giveUpChallenge(1L, 1L);

        // then

    }

    @Test
    @DisplayName("참여하지 않은 챌린지는 포기할 수 없다.")
    void test4() {
        // given
        User user = MockEntityFactory.mockUser(MockEntityFactory.mockIdentityVerification(), "테스트 유저");
        Challenge challenge = MockEntityFactory.mockChallenge(MockEntityFactory.mockImage(), "테스트 챌린지");
        given(userRepository.findById(any())).willReturn(Optional.of(user));
        given(challengeRepository.findById(any())).willReturn(Optional.of(challenge));
        given(participationRepository.findByChallengeAndUserAndClosedFalse(challenge, user)).willReturn(Optional.empty());

        // when

        // then
        Assertions.assertThatThrownBy(() -> participationService.giveUpChallenge(1L, 1L)).isInstanceOf(CBadRequestException.class);
    }

    @Test
    @DisplayName("참여했지만, 종료된 챌린지는 포기할 수 없다.")
    void test5() {
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
        Assertions.assertThatThrownBy(() -> participationService.giveUpChallenge(1L, 1L)).isInstanceOf(CBadRequestException.class);
    }
}