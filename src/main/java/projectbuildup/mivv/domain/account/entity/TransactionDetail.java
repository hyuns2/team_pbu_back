package projectbuildup.mivv.domain.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import projectbuildup.mivv.domain.challenge.entity.Challenge;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionDetail implements Comparable<TransactionDetail> {
    private final static String DATE_TIME_PATTERN = "yyyyMMddHHmmss";
    long amount;
    LocalDateTime time;

    public static TransactionDetail of(String amount, String date, String time) {
        return new TransactionDetail(Long.parseLong(amount), LocalDateTime.parse(date + time, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
    }


    @Override
    public int compareTo(TransactionDetail detail) {
        return detail.getTime().compareTo(this.time);
    }

    public boolean isValid(Challenge challenge, LocalDateTime startTime) {
        return challenge.getMinSavingAmount() <= this.amount && this.amount <= challenge.getMaxSavingAmount() && this.time.isAfter(startTime);
    }
}
