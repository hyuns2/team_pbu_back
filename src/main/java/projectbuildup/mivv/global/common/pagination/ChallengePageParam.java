package projectbuildup.mivv.global.common.pagination;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import projectbuildup.mivv.global.common.pagination.validation.PageValidation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class ChallengePageParam extends PageParam {
    @PageValidation(sortGroup = PageSortGroup.CHALLENGE)
    private PageSortType sortField = PageSortType.ID;
    @Override
    public Pageable toPageable() {
        return PageRequest.of(pageNumber, pageSize, sortField.toSort(direction));
    }
}
