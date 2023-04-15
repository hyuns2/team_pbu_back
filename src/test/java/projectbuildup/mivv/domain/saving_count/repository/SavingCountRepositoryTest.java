package projectbuildup.mivv.domain.saving_count.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import projectbuildup.mivv.domain.saving_count.entity.SavingCount;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.config.JpaAuditingConfig;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@DataJpaTest
@Import(JpaAuditingConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class SavingCountRepositoryTest {
    @Autowired
    SavingCountJpaRepository savingCountJpaRepository;

    @Test
    @DisplayName("")
    void test1() {
        // given
        SavingCount savingCount = new SavingCount();
        SavingCount saved = savingCountJpaRepository.save(savingCount);
        savingCount.addCount();

        // when
        SavingCount found = savingCountJpaRepository.findById(saved.getId()).orElseThrow();

        // then
        assertThat(found.getCount()).isEqualTo(1);
    }
}