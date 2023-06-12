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

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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
        Map<OpenBanking, String> connectionMap = Map.of(OpenBanking.CODEF, "0123456789");
        Account account = Account.builder()
                .accountNumbers("test")
                .bankType(BankType.CITY)
                .connectionMap(connectionMap)
                .build();

        // when
        Account saved = accountRepository.save(account);

        // then
        log.info("{}",saved);
        assertThat(saved.getConnectionMap().get(OpenBanking.CODEF)).isEqualTo("0123456789");
    }
}