package projectbuildup.mivv.domain.auth.service.component;

import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import projectbuildup.mivv.global.error.exception.CInternalServerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class KgClient {

    @Value("${kg.mid}")
    String MID;

    public ResponseBody request(String txId, String authRequestUrl) throws WebClientRequestException, IOException, ParseException {

        JSONObject resJson = null;
        JSONParser parser = new JSONParser();
        URL url = new URL(authRequestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        if (conn != null) {
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestMethod("POST");
            conn.setDefaultUseCaches(false);
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(9999);
            conn.setDoOutput(true);

            JSONObject reqJson = new JSONObject();
            reqJson.put("mid", "INIiasTest");  // 테스트 MID 입니다. 계약한 상점 MID 로 변경 필요
            reqJson.put("txId", txId);

            if (conn.getDoOutput()) {
                conn.getOutputStream().write(reqJson.toString().getBytes());
                conn.getOutputStream().flush();
                conn.getOutputStream().close();
            }

            conn.connect();
            if (conn.getResponseCode() == HttpServletResponse.SC_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                resJson = (JSONObject) parser.parse(br);
                br.close();
            }
        }
        ResponseBody responseData = ResponseBody.builder()
                .userName((String) resJson.get("userName"))
                .userBirthday((String) resJson.get("userBirthday"))
                .userPhone((String) resJson.get("userPhone"))
                .resultCode((String) resJson.get("resultCode"))
                .resultMsg((String) resJson.get("resultMsg"))
                .build();
        validate(responseData);
        return responseData;
    }

    private void validate(ResponseBody responseData) {
        if (responseData == null) {
            log.error("통신 실패");
            throw new CInternalServerException();
        }
        if (!responseData.isValid()) {
            log.error("KG 이니시스 본인인증 중 예외 발생 ! (에러 코드 : {})", responseData.getResultCode());
            throw new CInternalServerException();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    private static class RequestBody {
        private String mid;
        private String txId;
    }

    @Getter
    @ToString
    @Builder
    @AllArgsConstructor
    public static class ResponseBody {
        private String resultCode;
        private String resultMsg;
        private String txId;
        private String mTxId;
        private String svcCd;
        private String providerDevCd;
        private String userName;
        private String userPhone;
        private String userBirthday;
        private String userCi;
        private String signedData;

        public boolean isValid() {
            return resultCode.equals("0000");
        }
    }
}
