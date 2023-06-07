package projectbuildup.mivv.domain.remittance.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import projectbuildup.mivv.domain.account.entity.TransactionDetail;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


class RemittanceCheckerMockTest {
    @Test
    @DisplayName("Comparable을 이용해 날짜를 내림차순으로 정렬한다.")
    void test() {
        // given
        TransactionDetail detail1 = new TransactionDetail(1000, LocalDateTime.of(2023, 6, 5, 0, 0, 0));
        TransactionDetail detail2 = new TransactionDetail(1000, LocalDateTime.of(2023, 6, 6, 0, 0, 0));
        TransactionDetail detail3 = new TransactionDetail(1000, LocalDateTime.of(2023, 6, 7, 0, 0, 0));

        // when
        List<TransactionDetail> list = Arrays.asList(detail2, detail1, detail3);
        Collections.sort(list);

        // then
        assertThat(list).containsExactly(detail3, detail2, detail1);
    }

    @Test
    @DisplayName("Comparator를 이용해 날짜를 내림차순으로 정렬한다.")
    void test1() {
        // given
        TransactionDetail detail1 = new TransactionDetail(1000, LocalDateTime.of(2023, 6, 5, 0, 0, 0));
        TransactionDetail detail2 = new TransactionDetail(1000, LocalDateTime.of(2023, 6, 6, 0, 0, 0));
        TransactionDetail detail3 = new TransactionDetail(1000, LocalDateTime.of(2023, 6, 7, 0, 0, 0));

        // when
        List<TransactionDetail> list = Stream.of(detail2, detail1, detail3)
                .sorted((o1, o2) -> o2.getTime().compareTo(o1.getTime()))
                .toList();

        // then
        assertThat(list).containsExactly(detail3, detail2, detail1);
    }
}