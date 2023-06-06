package projectbuildup.mivv.domain.auth.service.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import projectbuildup.mivv.global.error.exception.CInternalServerException;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class KgClient {

    @Value("${kg.mid}")
    String MID;

    public ResponseBody request(String txId, String authRequestUrl) throws WebClientRequestException {
        WebClient webClient = WebClient.create();
        RequestBody requestBody = new RequestBody(MID, txId);
        ResponseBody responseData = webClient.post()
                .uri(authRequestUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(ResponseBody.class)
                .block();
        validate(responseData);
        return responseData;
    }

    private void validate(ResponseBody responseData){
        if (responseData == null){
            log.error("통신 실패");
            throw new CInternalServerException();
        }
        if (!responseData.isValid()){
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

        public boolean isValid(){
            return resultCode.equals("0000");
        }
    }
}
