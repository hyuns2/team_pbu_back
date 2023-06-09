package projectbuildup.mivv.domain.worthyConsumption.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public enum CheckConditionType {
    NOT_INVALID_DATE("발급 기간이 아닙니다."),
    ISSUED("쿠폰 발급 완료"),
    AVAILABLE("발급 가능");

    private final String title;
}
