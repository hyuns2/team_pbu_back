package projectbuildup.mivv.integrationtest.component;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import projectbuildup.mivv.domain.account.dto.AccountRegisterDto;
import projectbuildup.mivv.domain.account.entity.TransactionDetail;
import projectbuildup.mivv.domain.account.service.accountsystem.codefclient.CodefSandBoxClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class CodefSandBoxClientTest {
    @Autowired
    CodefSandBoxClient codefSandBoxClient;
    static final String CONNECTED_ID = "byi1wYwD40k8hEIiXl6bRF";


    @Test
    @DisplayName("API 사용을 위한 토큰을 발급한다.")
    void test() {
        // given

        // when
        String accessToken = codefSandBoxClient.issueToken();

        // then
        assertThat(accessToken).isNotNull();
    }

    @Test
    @DisplayName("커넥티드 아이디를 생성한다.")
    void test1() {
        // given

        // when
        Map<String, Object> dataField = codefSandBoxClient.createConnectedId(new AccountRegisterDto("004", "", "user_id", "user_password", ""));
        String result = (String) dataField.get("connectedId");

        // then
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("회원이_해당_은행에_보유한_계좌_목록을_조회한다.")
    void test2() {
        // given

        // when
        Map<String, Object> dataField = codefSandBoxClient.getOwnAccounts("004", CONNECTED_ID);
        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) dataField.get("resDepositTrust");
        /*
            list = [
                    {resAccountName=저축예금, resAccountStartDate=20120907, resAccountCurrency=KRW, resAccountBalance=874890, resAccountNickName=, resAccountEndDate=, resAccountLifetime=, resAccount=06170204000000, resAccountDeposit=11, resLastTranDate=, resAccountDisplay=061702-04-000000},
                    {resAccountName=직장인우대통장-저축예금, resAccountStartDate=20060413, resAccountCurrency=KRW, resAccountBalance=0, resAccountNickName=급여통장, resAccountEndDate=, resAccountLifetime=, resAccount=23850204000000, resAccountDeposit=11, resLastTranDate=, resAccountDisplay=238502-04-000000},
                    {resAccountName=OO국민재형저축, resAccountStartDate=20151228, resAccountCurrency=KRW, resAccountBalance=13110000, resAccountNickName=, resAccountEndDate=, resAccountLifetime=, resAccount=54780300000000, resAccountDeposit=12, resLastTranDate=, resAccountDisplay=547803-00-000000}
            ]
        */

        List<String> accountList = list.stream()
                .map(elem -> (String) elem.get("resAccount"))
                .toList();

        // then
        assertThat(accountList).containsExactly("06170204000000", "23850204000000", "54780300000000");

    }

    @Test
    @DisplayName("계좌의 입출금 거래 내역을 조회한다.")
    void test3() {
        //given

        // when
        Map<String, Object> dataField = codefSandBoxClient.getTransactionList(CONNECTED_ID, "0004", "012345678", LocalDate.now());
        List<HashMap<String, Object>> listMap = (List<HashMap<String, Object>>) dataField.get("resTrHistoryList");
        /*
            listMap = [
                {resAccountTrTime=200631, resAccountDesc4=구로디, resAccountDesc3=54780300024283,43회차, resAccountDesc2=기일출금, resAccountDesc1=, resAccountTrDate=20190531, resAccountOut=300000, resAfterTranBalance=309970, resAccountIn=0, tranDesc=},
                {resAccountTrTime=162920, resAccountDesc4=AA증권, resAccountDesc3=, resAccountDesc2=전자금융, resAccountDesc1=, resAccountTrDate=20190529, resAccountOut=0, resAfterTranBalance=609970, resAccountIn=600000, tranDesc=},
                {resAccountTrTime=200616, resAccountDesc4=구로디, resAccountDesc3=54780300024283,42회차, resAccountDesc2=기일출금, resAccountDesc1=, resAccountTrDate=20190523, resAccountOut=300000, resAfterTranBalance=9970, resAccountIn=0, tranDesc=},
                {resAccountTrTime=141319, resAccountDesc4=OO은행, resAccountDesc3=XXX, resAccountDesc2=전자금융, resAccountDesc1=, resAccountTrDate=20190523, resAccountOut=0, resAfterTranBalance=309970, resAccountIn=300000, tranDesc=},
                {resAccountTrTime=033808, resAccountDesc4=양평동, resAccountDesc3=이자세금:원, resAccountDesc2=결산이자, resAccountDesc1=, resAccountTrDate=20190511, resAccountOut=0, resAfterTranBalance=9970, resAccountIn=11, tranDesc=},
                {resAccountTrTime=203432, resAccountDesc4=구로디, resAccountDesc3=54780300024283,41회차, resAccountDesc2=기일출금, resAccountDesc1=, resAccountTrDate=20190401, resAccountOut=300000, resAfterTranBalance=9959, resAccountIn=0, tranDesc=}
            ]
        */

        List<TransactionDetail> returnList = new ArrayList<>();
        for (Map<String, Object> elem : listMap) {
            String amount = (String) elem.get("resAccountIn");
            if (amount.equals("0")) {
                continue;
            }
            TransactionDetail detail = TransactionDetail.of(amount, (String) elem.get("resAccountTrDate"), (String) elem.get("resAccountTrTime"));
            returnList.add(detail);
        }
        //      [
        //          TransactionDetail(amount=600000, time=2019-05-29T16:29:20),
        //          TransactionDetail(amount=300000, time=2019-05-23T14:13:19),
        //          TransactionDetail(amount=11, time=2019-05-11T03:38:08)
        //       ]

        //then
        assertThat(returnList.size()).isEqualTo(3);
        assertThat(returnList.get(0).getAmount()).isEqualTo(600000L);
        assertThat(returnList.get(1).getAmount()).isEqualTo(300000L);
        assertThat(returnList.get(2).getAmount()).isEqualTo(11L);
    }

}

