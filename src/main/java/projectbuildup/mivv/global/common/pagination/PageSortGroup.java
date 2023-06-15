package projectbuildup.mivv.global.common.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public enum PageSortGroup {
    CHALLENGE(List.of(PageSortType.ID, PageSortType.CHALLENGE_TITLE, PageSortType.CHALLENGE_MIN_SAVING_AMOUNT, PageSortType.CHALLENGE_END_TIME, PageSortType.CREATED_TIME)),
    DEFAULT(List.of(PageSortType.ID, PageSortType.CREATED_TIME)),
    EMPTY(List.of(PageSortType.EMPTY));

    private final List<PageSortType> sortTypes;
}