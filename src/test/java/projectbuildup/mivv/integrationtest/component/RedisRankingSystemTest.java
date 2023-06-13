package projectbuildup.mivv.integrationtest.component;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RedisRankingSystemTest {
    @Test
    @DisplayName("")
    void test() {
        // given
        List<Integer> list = List.of(0, 1, 2, 3, 4);
        // when

        List<Integer> upper = list.subList(0, 2);
        List<Integer> lower = list.subList(3, 5);

        // then
        Assertions.assertThat(upper).containsExactly(0, 1);
        Assertions.assertThat(lower).containsExactly(3, 4);
    }

    @Test
    @DisplayName("")
    void test1() {
        // given
        List<Integer> list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add(2);
        list.add(3);
        list.add(4);
        // when

        List<Integer> upper = list.subList(0, 2);
        List<Integer> lower = list.subList(3, 5);

        // then
        Assertions.assertThat(upper).containsExactly(null, null);
        Assertions.assertThat(lower).containsExactly(3, 4);
    }
}