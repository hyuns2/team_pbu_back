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
    private final static String IN_AMOUNT_FIELD = "resAccountIn";
    private final static String OUT_AMOUNT_FIELD = "resAccountOut";
    private final static String DATE_FIELD = "resAccountTrDate";
    private final static String TIME_FIELD = "resAccountTrTime";

    @Override
    public List<Map<String, String>> getDepositHistory(User user) {
        List<Map<String, String>> returnList = new ArrayList<>();

        List<Map<String, Object>> list = getList(user);
        for (Map<String, Object> map : list) {
            HashMap<String, String> returnMap = getReturnMap(map, IN_AMOUNT_FIELD);
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
            HashMap<String, String> returnMap = getReturnMap(map, OUT_AMOUNT_FIELD);
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
        returnMap.put(field, amount);
        returnMap.put(DATE_FIELD, (String) stringObjectHashMap.get("resAccountTrDate"));
        returnMap.put(TIME_FIELD, (String) stringObjectHashMap.get("resAccountTrTime"));
        return returnMap;
    }

    @Override
    public String getInAmountField() {
        return IN_AMOUNT_FIELD;
    }

    @Override
    public String getOutAmountField() {
        return OUT_AMOUNT_FIELD;
    }

    @Override
    public String getDateField() {
        return DATE_FIELD;
    }

    @Override
    public String getTimeField() {
        return TIME_FIELD;
    }
}
