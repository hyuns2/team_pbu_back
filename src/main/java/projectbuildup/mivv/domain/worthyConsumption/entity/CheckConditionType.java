package projectbuildup.mivv.domain.worthyConsumption.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public enum CheckConditionType {
    NOT_DATE("발급 기간이 아닙니다."),
    ALREADY_SPEND("가치소비의 쿠폰이 이미 소진되었습니다."),
    OK("발급 가능한 쿠폰이 있습니다.");

    private final String title;
}
