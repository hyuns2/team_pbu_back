package projectbuildup.mivv.global.common.pagination.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import projectbuildup.mivv.global.common.pagination.PageSortGroup;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {PageValidator.class})
public @interface PageValidation {
    String message() default "올바르지 않은 페이지 정보입니다.";
    PageSortGroup sortGroup() default PageSortGroup.EMPTY;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
