package projectbuildup.mivv.integrationtest.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import projectbuildup.mivv.domain.remittance.dto.RemittanceDto;
import projectbuildup.mivv.domain.saving_count.entity.SavingCount;
import projectbuildup.mivv.domain.saving_count.repository.SavingCountRepository;
import projectbuildup.mivv.integrationtest.setting.IntegrationTest;
import projectbuildup.mivv.integrationtest.setting.WithAuthUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RemittanceApiTest extends IntegrationTest {

    final static String REMIT_API = "/api/remittance/challenges/{challengeId}";
    final static String STATUS_API = "/api/remittance/status";

    @Autowired
    SavingCountRepository savingCountRepository;

    @Test
    @WithAuthUser(role = "USER", id = "1")
    @DisplayName("송금 금액을 확인하고, 절약 정보를 갱신한다. ")
    void test() throws Exception {
        // given
        RemittanceDto.RemitRequest requestDto = new RemittanceDto.RemitRequest(1L, 1L, 1000L);
        // when
        ResultActions actions = mvc.perform(post(REMIT_API, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        // then
        actions.andExpect(status().isOk());
    }

    @Test
    @WithAuthUser(role = "USER", id = "1")
    @DisplayName("송금 금액이 적으면 예외가 발생한다. ")
    void test1() throws Exception {
        // given
        RemittanceDto.RemitRequest requestDto = new RemittanceDto.RemitRequest(1L, 1L, 100L);
        // when
        ResultActions actions = mvc.perform(post(REMIT_API, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        // then
        actions.andExpect(status().isBadRequest());
    }

    @Test
    @WithAuthUser(role = "USER", id = "1")
    @DisplayName("송금 금액이 크면 예외가 발생한다. ")
    void test2() throws Exception {
        // given
        RemittanceDto.RemitRequest requestDto = new RemittanceDto.RemitRequest(1L, 1L, 10000L);
        // when
        ResultActions actions = mvc.perform(post(REMIT_API, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        // then
        actions.andExpect(status().isBadRequest());
    }

    @Test
    @WithAuthUser(role = "USER", id = "1")
    @DisplayName("일일 참여 횟수를 넘으면 예외가 발생한다. ")
    void test3() throws Exception {
        // given

        SavingCount savingCount = savingCountRepository.findById(1L).get();
        for (int i = 0; i < 10; i++) {
            savingCount.addCount();
        }
        savingCountRepository.save(savingCount);
        RemittanceDto.RemitRequest requestDto = new RemittanceDto.RemitRequest(1L, 1L, 1000L);
        // when
        ResultActions actions = mvc.perform(post(REMIT_API, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        // then
        actions.andExpect(status().isBadRequest());
    }

    @Test
    @WithAuthUser(role = "USER", id = "1")
    @DisplayName("사용자의 절약 상태를 조회한다.")
    void test4() throws Exception {
        // given

        // when
        ResultActions actions = mvc.perform(get(STATUS_API));

        // then
        actions.andExpect(status().isOk());
    }
}
