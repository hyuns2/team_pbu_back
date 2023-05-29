package projectbuildup.mivv.domain.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetail {
    private final static String DATE_TIME_PATTERN = "yyyyMMddHHmmss";
    long amount;
    LocalDateTime time;

    public static TransactionDetail of(String amount, String date, String time) {
        return new TransactionDetail(Long.parseLong(amount), LocalDateTime.parse(date + time, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
    }
}
