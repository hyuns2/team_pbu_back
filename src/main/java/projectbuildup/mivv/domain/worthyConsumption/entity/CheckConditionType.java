package projectbuildup.mivv.domain.worthyConsumption.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
@RequiredArgsConstructor
public enum CheckConditionType {
    NOT_DATE("발급 기간이 아닙니다."),
    ALREADY_SPEND("이미 소진된 쿠폰입니다."),
    OK("발급 가능한 쿠폰입니다.");

    private final String title;
}
