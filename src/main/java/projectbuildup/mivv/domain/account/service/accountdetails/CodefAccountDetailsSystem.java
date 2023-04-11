package projectbuildup.mivv.domain.account.service.accountdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.account.entity.Account;
import projectbuildup.mivv.domain.account.entity.OpenBanking;
import projectbuildup.mivv.domain.account.service.accountsystem.CodefClient;
import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CodefAccountDetailsSystem implements AccountDetailsSystem {
    private final CodefClient codefClient;
    public final static String AMOUNT_FIELD = "resAccountIn";
    public final static String DATE_FIELD = "resAccountTrDate";
    public final static String TIME_FIELD = "resAccountTrTime";

    @Override
    public List<Map<String, String>> getDepositHistory(User user) {
        List<Map<String, String>> returnList = new ArrayList<>();

        List<Map<String, Object>> list = getList(user);
        for (Map<String, Object> map : list) {
            HashMap<String, String> returnMap = getReturnMap(map, "resAccountIn");
            if (returnMap != null) {
                returnList.add(returnMap);
            }
        }
        return returnList;
    }

    @Override
    public List<Map<String, String>> getWithdrawHistory(User user) {
        List<Map<String, String>> returnList = new ArrayList<>();

        List<Map<String, Object>> list = getList(user);
        for (Map<String, Object> map : list) {
            HashMap<String, String> returnMap = getReturnMap(map, "resAccountOut");
            if (returnMap != null) {
                returnList.add(returnMap);
            }
        }
        return returnList;
    }

    private List<Map<String, Object>> getList(User user) {
        Account account = user.getAccount();
        String connectedId = account.getConnectionMap().get(OpenBanking.CODEF);
        String bankCode = account.getBankType().getCode();
        String accountNumbers = account.getAccountNumbers();
        Map<String, Object> result = codefClient.getHistory(connectedId, bankCode, accountNumbers, LocalDate.now());
        Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
        List<Map<String, Object>> listMap = (List<Map<String, Object>>) dataMap.get("resTrHistoryList");
        return listMap;
    }

    private HashMap<String, String> getReturnMap(Map<String, Object> stringObjectHashMap, String field) {
        HashMap<String, String> returnMap = new HashMap<>();
        String amount = (String) stringObjectHashMap.get(field);
        if (amount.equals("0")) {
            return null;
        }
        returnMap.put(AMOUNT_FIELD, amount);
        returnMap.put(DATE_FIELD, (String) stringObjectHashMap.get("resAccountTrDate"));
        returnMap.put(TIME_FIELD, (String) stringObjectHashMap.get("resAccountTrTime"));
        return returnMap;
    }
}
