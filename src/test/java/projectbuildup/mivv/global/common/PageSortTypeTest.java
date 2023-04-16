package projectbuildup.mivv.global.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.global.common.pagination.ChallengePageParam;
import projectbuildup.mivv.global.common.pagination.PageParam;
import projectbuildup.mivv.global.common.pagination.PageSortGroup;
import projectbuildup.mivv.global.common.pagination.PageSortType;
import projectbuildup.mivv.global.config.JpaAuditingConfig;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@Import(JpaAuditingConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PageSortTypeTest {
    @Autowired
    ChallengeRepository challengeRepository;

    @Test
    @DisplayName("도메인마다 정해진 정렬 방법만 사용할 수 있다.")
    void test1() {
        // given
        PageParam pageRequest = ChallengePageParam.builder()
                .pageSize(1)
                .pageNumber(0)
                .sortField(PageSortType.CHALLENGE_AMOUNT)
                .direction(Sort.Direction.ASC)
                .build();

        // when
//        boolean result1 = pageRequest.validate(PageSortGroup.CHALLENGE);
//        boolean result2 = pageRequest.validate(PageSortGroup.EMPTY);
//
//        // then
//        assertThat(result1).isTrue();
//        assertThat(result2).isFalse();
    }

    @Test
    @DisplayName("정렬 방법에 따라 Repository의 메서드가 결정된다.")
    void test2() {
        // given
        PageParam pageRequest = ChallengePageParam.builder()
                .pageSize(1)
                .pageNumber(0)
                .sortField(PageSortType.CHALLENGE_AMOUNT)
                .direction(Sort.Direction.ASC)
                .build();

        // when
        Pageable pageable = pageRequest.toPageable();

        // then
        log.info("{}", pageable);
        assertThat(pageable.getPageSize()).isEqualTo(1);
        assertThat(pageable.getPageNumber()).isEqualTo(0);
        assertThat(pageable.getSort()).isEqualTo(Sort.by(Sort.Direction.ASC, "remittanceOnceLimit"));
    }
}