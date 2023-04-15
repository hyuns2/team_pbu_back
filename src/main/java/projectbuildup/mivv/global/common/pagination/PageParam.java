package projectbuildup.mivv.global.common.pagination;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public abstract class PageParam {
    @Parameter(description = "페이지 크기 (기본값 : 10)", example = "10")
    protected int pageSize = 10;
    @Parameter(description = "페이지 번호(0..N) (기본값 : 0)", example = "0")
    protected int pageNumber = 0;
    @Parameter(description = "정렬 기준 (기본값 : ID 정렬)")
    private PageSortType sortField = PageSortType.EMPTY;
    @Parameter(description = "정렬 방향 (기본값 : 오름차순)")
    protected Sort.Direction direction = Sort.Direction.ASC;

    public abstract Pageable toPageable();
}
