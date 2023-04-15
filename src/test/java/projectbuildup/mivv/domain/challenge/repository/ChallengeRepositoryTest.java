package projectbuildup.mivv.domain.challenge.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.participation.repository.ParticipationRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.config.JpaAuditingConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import(JpaAuditingConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class ChallengeRepositoryTest {
    @Autowired
    ChallengeRepository challengeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ParticipationRepository participationRepository;

    @Test
    @DisplayName("참여중인 챌린지(제목, ASC/DESC) 조회")
    void test1() {
        // given
        User user = new User();
        Challenge challenge1 = Challenge.builder().mainTitle("가나다").build();
        Challenge challenge2 = Challenge.builder().mainTitle("라마바").build();
        Challenge challenge3 = Challenge.builder().mainTitle("아자차").build();
        Challenge challenge4 = Challenge.builder().mainTitle("카타파").build();
        Participation participation1 = new Participation(user, challenge1);
        Participation participation2 = new Participation(user, challenge2);
        userRepository.save(user);
        challengeRepository.save(challenge1);
        challengeRepository.save(challenge2);
        challengeRepository.save(challenge3);
        challengeRepository.save(challenge4);
        participationRepository.save(participation1);
        participationRepository.save(participation2);

        // when
        List<Challenge> foundAsc = challengeRepository.findOngoingChallenge(user, PageRequest.of(0,10, Sort.by(Sort.Direction.ASC, "mainTitle"))).getContent();
        List<Challenge> foundDesc = challengeRepository.findOngoingChallenge(user, PageRequest.of(0,10, Sort.by(Sort.Direction.DESC, "mainTitle"))).getContent();

        // then
        log.info("{}",foundAsc);
        log.info("{}",foundDesc);
        assertThat(foundAsc).containsExactly(challenge1, challenge2);
        assertThat(foundDesc).containsExactly(challenge2, challenge1);
    }

    @Test
    @DisplayName("참여가능한 챌린지(제목, ASC/DESC) 조회")
    void test2() {
        // given
        User user = new User();
        Challenge challenge1 = Challenge.builder().mainTitle("가나다").build();
        Challenge challenge2 = Challenge.builder().mainTitle("라마바").build();
        Challenge challenge3 = Challenge.builder().mainTitle("아자차").build();
        Challenge challenge4 = Challenge.builder().mainTitle("카타파").build();
        Participation participation1 = new Participation(user, challenge1);
        Participation participation2 = new Participation(user, challenge2);
        userRepository.save(user);
        challengeRepository.save(challenge1);
        challengeRepository.save(challenge2);
        challengeRepository.save(challenge3);
        challengeRepository.save(challenge4);
        participationRepository.save(participation1);
        participationRepository.save(participation2);

        // when
        List<Challenge> foundAsc = challengeRepository.findJoinableChallenge(user, PageRequest.of(0,10, Sort.by(Sort.Direction.ASC, "mainTitle"))).getContent();
        List<Challenge> foundDesc = challengeRepository.findJoinableChallenge(user, PageRequest.of(0,10, Sort.by(Sort.Direction.DESC, "mainTitle"))).getContent();

        // then
        log.info("{}",foundAsc);
        log.info("{}",foundDesc);
        assertThat(foundAsc).containsExactly(challenge3, challenge4);
        assertThat(foundDesc).containsExactly(challenge4, challenge3);
    }
}