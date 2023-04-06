package projectbuildup.mivv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.codef.api.EasyCodef;
import io.codef.api.EasyCodefServiceType;
import io.codef.api.EasyCodefUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import projectbuildup.mivv.global.error.exception.CResourceNotFoundException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CodefTest {

    static final String CLIENT_ID = "ef27cfaa-10c1-4470-adac-60ba476273f9";
    static final String CLIENT_SECRET = "83160c33-9045-4915-86d8-809473cdf5c3";
    static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx1ohEpXp9O0eqAG4OQuNnyj2RvqTMqsLW3ZYmZxePiujf2HsW4VXQ4T1/S0tIP5NgGgY2azeOs4pPGVnFg9B8j3aqFJR0hgHtGO1DiuaOLLSvJe7k/89MahdozXk/D7H6g6APg6g7T7ZabGJwG1ED2FzVH/HtqdfJ/g4XGmSlconTS8K0U2kFgxkK1jjhdaZxCgZ5c7cYiDyQ5EgEHDNdAQSi/OIkDdZF6kXezg5xVo2Q5uvsZQHhb9JHaO1cXfYjDH1EuYdf7KwPYpLfvLE8M1glPDKlZ0Yea5RwBsRbjNUJQVaK1CMXq94+vdLHMMKhbd9IwCe4bRLcaCV8wDRfwIDAQAB";
    static final String CONNECTED_ID = "byi1wYwD40k8hEIiXl6bRF";


    @Test
    @DisplayName("API 사용을 위한 토큰을 발급한다.")
    void API_사용을_위한_토큰을_발급한다() throws IOException {
        // given
        EasyCodef codef = new EasyCodef();
        codef.setClientInfo(CLIENT_ID, CLIENT_SECRET);
        codef.setPublicKey(PUBLIC_KEY);

        String accessToken = codef.requestToken(EasyCodefServiceType.API);
        System.out.println(accessToken);

        // when

        // then
        assertThat(accessToken).isNotNull();
    }

    @Test
    @DisplayName("커넥티드 아이디를 생성한다.")
    void 커넥티드_아이디를_생성한다() throws UnsupportedEncodingException, JsonProcessingException, InterruptedException {
        // given
        EasyCodef codef = new EasyCodef();
        codef.setClientInfo(CLIENT_ID, CLIENT_SECRET);
        codef.setPublicKey(PUBLIC_KEY);

        List<HashMap<String, Object>> accountList = new ArrayList<>();
        HashMap<String, Object> accountMap = new HashMap<>();
        accountMap.put("countryCode", "KR");
        accountMap.put("businessType", "BK");
        accountMap.put("organization", "004");
        accountMap.put("clientType", "P");
        accountMap.put("loginType", "1");
        accountMap.put("id", "lhs8701");
        try {
            accountMap.put("password", EasyCodefUtil.encryptRSA("user_password", codef.getPublicKey()));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        accountList.add(accountMap);
        HashMap<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("accountList", accountList);

        // when
        String result = codef.createAccount(EasyCodefServiceType.SANDBOX, parameterMap);
        System.out.println(result);

        // then
        HashMap<String, Object> responseMap = new ObjectMapper().readValue(result, HashMap.class);
        HashMap<String, Object> resultMap = (HashMap<String, Object>) responseMap.get("data");
        String connectedId = (String) resultMap.get("connectedId");
        System.out.println("connectedId = " + connectedId);
        assertThat(connectedId).isNotNull();
    }

    @Test
    @DisplayName("회원이_해당_은행에_보유한_계좌_목록을_조회한다.")
    void 회원이_해당_은행에_보유한_계좌_목록을_조회한다() throws UnsupportedEncodingException, JsonProcessingException, InterruptedException {
        // given
        EasyCodef codef = new EasyCodef();
        codef.setClientInfo(CLIENT_ID, CLIENT_SECRET);
        codef.setPublicKey(PUBLIC_KEY);

        // when
        HashMap<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("organization", "0004");
        parameterMap.put("connectedId", CONNECTED_ID);
        String productUrl = "/v1/kr/bank/p/account/account-list";
        String result = codef.requestProduct(productUrl, EasyCodefServiceType.SANDBOX, parameterMap);
        System.out.println("result = " + result);

        // then
        HashMap<String, Object> responseMap = new ObjectMapper().readValue(result, HashMap.class);
        HashMap<String, Object> dataMap = (HashMap<String, Object>) responseMap.get("data");
        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) dataMap.get("resDepositTrust");
        List<String> accountList = list.stream()
                .map(elem -> (String) elem.get("resAccount"))
                .toList();
        System.out.println("accountList = " + accountList);
    }
}

/*

resDepositTrustList = [

{resAccountName=저축예금, resAccountStartDate=20120907, resAccountCurrency=KRW, resAccountBalance=874890, resAccountNickName=, resAccountEndDate=, resAccountLifetime=, resAccount=06170204000000, resAccountDeposit=11, resLastTranDate=, resAccountDisplay=061702-04-000000},

{resAccountName=직장인우대통장-저축예금, resAccountStartDate=20060413, resAccountCurrency=KRW, resAccountBalance=0, resAccountNickName=급여통장, resAccountEndDate=, resAccountLifetime=, resAccount=23850204000000, resAccountDeposit=11, resLastTranDate=, resAccountDisplay=238502-04-000000},

{resAccountName=OO국민재형저축, resAccountStartDate=20151228, resAccountCurrency=KRW, resAccountBalance=13110000, resAccountNickName=, resAccountEndDate=, resAccountLifetime=, resAccount=54780300000000, resAccountDeposit=12, resLastTranDate=, resAccountDisplay=547803-00-000000}

]
 */