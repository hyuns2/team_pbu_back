package projectbuildup.mivv.domain.account.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
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
        Account account =Account.builder()
                .bankType(BankType.CITY)
                .accountNumbers("0123456789")
                .platform(OpenBanking.CODEF)
                .connectionId("11223344")
                .build();

        // when
        Account saved = accountRepository.save(account);

        // then
        log.info("{}",saved);
        assertThat(saved.getAccountNumbers()).isEqualTo("0123456789");
    }

    @Test
    @DisplayName("계좌 저장시 커넥티드아이디가 주어지지 않으면 예외를 발생시킨다.")
    void 계좌_저장시_커넥티드아이디가_주어지지_않으면_예외_발생() {
        // given

        // when

        // then
        assertThatThrownBy(() -> Account.builder().bankType(BankType.CITY).accountNumbers("0123456789").build())
                .isInstanceOf(CInternalServerException.class);
    }
}