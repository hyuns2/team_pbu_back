package projectbuildup.mivv.integrationtest.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import projectbuildup.mivv.integrationtest.setting.IntegrationTest;
import projectbuildup.mivv.integrationtest.setting.WithAuthUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RemittanceApiTest extends IntegrationTest {

    final static String REMIT_API = "/api/remittance/challenges/{challengeId}";
    final static String STATUS_API = "/api/remittance/status";
    final static String DUMMY_RANKING_API = "/api/ref/ranking-data";


    @Test
    @WithAuthUser(role = "USER", id = "1")
    @DisplayName("사용자의 절약 상태를 조회한다.")
    void test2() throws Exception {
        // given

        // when
        mvc.perform(get(DUMMY_RANKING_API));    //레디스 랭킹 데이터 초기화
        ResultActions actions = mvc.perform(get(STATUS_API));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("total").value(16000)) // 총 절약 금액 16000원
                .andExpect(jsonPath("ranks[0].rank").value(1)) // 전체 챌린지 순위 - 1등
                .andExpect(jsonPath("ranks[1].rank").value(4)) // 1번 챌린지 순위 - 4등
                .andExpect(jsonPath("ranks[2].rank").value(1)); // 2번 챌린지 순위 - 1등
    }
}
