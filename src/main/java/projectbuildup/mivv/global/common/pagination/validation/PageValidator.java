package projectbuildup.mivv.global.common.pagination.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import projectbuildup.mivv.global.common.pagination.PageSortGroup;
import projectbuildup.mivv.global.common.pagination.PageSortType;


public class PageValidator implements ConstraintValidator<PageValidation, PageSortType> {
    private PageSortGroup pageSortGroup;

    @Override
    public void initialize(PageValidation constraintAnnotation) {
        pageSortGroup = constraintAnnotation.sortGroup();
    }

    @Override
    public boolean isValid(PageSortType value, ConstraintValidatorContext context) {
        return pageSortGroup.getSortTypes().contains(value) ;
    }
}
