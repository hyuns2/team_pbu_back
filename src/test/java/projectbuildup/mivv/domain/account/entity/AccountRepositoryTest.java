package projectbuildup.mivv.domain.account.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import projectbuildup.mivv.common.MockEntity;
import projectbuildup.mivv.domain.account.repository.AccountRepository;
import projectbuildup.mivv.global.config.JpaAuditingConfig;
import projectbuildup.mivv.global.error.exception.CInternalServerException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@DataJpaTest
@Import(JpaAuditingConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("계좌 저장시 커넥티드아이디를 함께 저장한다.")
    void 계좌_저장시_커넥티드아이디를_함께_저장한다() {
        // given
        Account account = MockEntity.MOCK_ACCOUNT;

        // when
        Account saved = accountRepository.save(account);

        // then
        log.info("{}",saved);
        assertThat(saved.getAccountNumbers()).isEqualTo("0123456789");
    }

}