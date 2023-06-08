package projectbuildup.mivv.domain.account.service.accountsystem.codefclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import projectbuildup.mivv.domain.account.dto.AccountRegisterDto;
import projectbuildup.mivv.global.error.exception.CIllegalArgumentException;
import projectbuildup.mivv.global.error.exception.CInternalServerException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public interface CodefClient {
    String issueToken();

    Map<String, Object> createConnectedId(AccountRegisterDto accountDto);

    Map<String, Object> getOwnAccounts(String bankType, String connectedId);

    Map<String, Object> getTransactionList(String connectedId, String bankCode, String accountNumbers, LocalDate startDate);

    Map<String, Object> certifyTransfer(String organizationCode, String accountNumbers);

    /**
     * 결과 JSON을 바탕으로 에러 코드를 확인하고, 정상 응답인 경우 data 필드를 리턴합니다.
     *
     * @param json JSON 응답 원본
     * @return 응답값의 data 필드
     */
    default Map<String, Object> getDataField(String json) {
        try {
            HashMap<String, Object> jsonMap = new ObjectMapper().readValue(json, HashMap.class);
            HashMap<String, Object> resultMap = (HashMap<String, Object>) jsonMap.get("result");
            String code = (String) resultMap.get("code");
            if (code.equals("CF-00000")) {
                return (HashMap<String, Object>) jsonMap.get("data");
            }
            throw new CIllegalArgumentException("인증 과정에서 오류가 발생했습니다. 코드에프 에러코드: " + code);
        } catch (JsonProcessingException e) {
            throw new CInternalServerException();
        }
    }
}
