package projectbuildup.mivv.domain.account.service.accountdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.account.entity.Account;
import projectbuildup.mivv.domain.account.entity.OpenBanking;
import projectbuildup.mivv.domain.account.entity.TransactionDetail;
import projectbuildup.mivv.domain.account.service.accountsystem.CodefClient;
import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDate;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CodefAccountDetailsSystem implements AccountDetailsSystem {
    private final CodefClient codefClient;
    private final static String HISTORY_FIELD = "resTrHistoryList";
    private final static String IN_AMOUNT_FIELD = "resAccountIn";
    private final static String OUT_AMOUNT_FIELD = "resAccountOut";
    private final static String DATE_FIELD = "resAccountTrDate";
    private final static String TIME_FIELD = "resAccountTrTime";

    /**
     * 사용자의 입금 내역을 조회합니다.
     *
     * @param user      사용자
     * @param startDate 조회 시작 날짜
     * @return [입금액, 날짜, 시간]로 이루어진 목록
     */
    @Override
    public List<TransactionDetail> getDepositHistory(User user, LocalDate startDate) {
        return getHistory(user, startDate, IN_AMOUNT_FIELD);
    }

    /**
     * 사용자의 출금 내역을 조회합니다.
     *
     * @param user      사용자
     * @param startDate 조회 시작 날짜
     * @return [출금액, 날짜, 시간]로 이루어진 목록
     */
    @Override
    public List<TransactionDetail> getWithdrawHistory(User user, LocalDate startDate) {
        return getHistory(user, startDate, OUT_AMOUNT_FIELD);
    }

    /**
     * 코드에프의 수시입출내역 조회 API를 호출하여, startDate ~ 현재까지의 계좌 내역을 반환합니다.
     * amountField에 따라 입금/출금 내역을 선택해 조회합니다.
     *
     * @param user        사용자
     * @param startDate   조회 시작일자
     * @param amountField 입금/출금 선택
     * @return [금액, 날짜, 시간]로 이루어진 목록
     */
    private List<TransactionDetail> getHistory(User user, LocalDate startDate, String amountField) {
        List<TransactionDetail> returnList = new ArrayList<>();
        List<Map<String, Object>> transactionList = getTransactionList(user, startDate);
        for (Map<String, Object> elem : transactionList) {
            getTransactionDetail(elem, amountField).ifPresent(returnList::add);
        }
        return returnList;
    }


    private List<Map<String, Object>> getTransactionList(User user, LocalDate startDate) {
        Account account = user.getAccount();
        String connectedId = account.getConnectionMap().get(OpenBanking.CODEF);
        String bankCode = account.getBankType().getCode();
        String accountNumbers = account.getAccountNumbers();
        Map<String, Object> dataMap = codefClient.getTransactionList(connectedId, bankCode, accountNumbers, startDate);
        return (List<Map<String, Object>>) dataMap.get(HISTORY_FIELD);
    }

    private Optional<TransactionDetail> getTransactionDetail(Map<String, Object> elem, String field) {
        String amount = (String) elem.get(field);
        if (amount.equals("0")) {
            return Optional.empty();
        }
        return Optional.of(TransactionDetail.of(amount, (String) elem.get(DATE_FIELD), (String) elem.get(TIME_FIELD)));
    }
}
