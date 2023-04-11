package projectbuildup.mivv.domain.remittance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.YearMonth;

@AllArgsConstructor
@Getter
public class RemittanceCount {
    public YearMonth yearMonth;
    public long count;
}
