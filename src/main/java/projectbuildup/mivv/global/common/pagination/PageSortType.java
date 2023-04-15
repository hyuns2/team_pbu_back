package projectbuildup.mivv.global.common.pagination;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
public enum PageSortType {
    CHALLENGE_TITLE("mainTitle"),
    CHALLENGE_END_TIME("endDate"),
    CHALLENGE_AMOUNT("remittanceOnceLimit"),
    CREATED_TIME("createdTime"),
    ID("id"),
    EMPTY(null);

    private final String sortField;

    public Sort toSort(Sort.Direction direction) {
        return Sort.by(direction, sortField);
    }
}
