package projectbuildup.mivv.domain.account.service.accountsystem.codefclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import projectbuildup.mivv.domain.account.dto.AccountRegisterDto;
import projectbuildup.mivv.global.error.exception.CCodefException;
import projectbuildup.mivv.global.error.exception.CInternalServerException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public interface CodefClient {
    String SUCCESS = "CF-00000";

    String issueToken();

    Map<String, Object> createConnectedId(AccountRegisterDto accountDto);

    Map<String, Object> getOwnAccounts(String bankType, String connectedId);

    Map<String, Object> getTransactionList(String connectedId, String bankCode, String accountNumbers, LocalDate startDate);

    Map<String, Object> certifyTransfer(String organizationCode, String accountNumbers);

    Map<String, Object> holderAuthentication(String organizationCode, String accountNumbers, String birthDate);

    Map<String, Object> unlinkAccount(String connectedId, String code);

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
            String message = (String) resultMap.get("message");
            if (code.equals(SUCCESS)) {
                return (HashMap<String, Object>) jsonMap.get("data");
            }
            throw new CCodefException(code, message);
        } catch (JsonProcessingException e) {
            throw new CInternalServerException();
        }
    }


}
