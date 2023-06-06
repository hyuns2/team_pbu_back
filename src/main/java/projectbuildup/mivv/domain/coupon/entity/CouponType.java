package projectbuildup.mivv.domain.coupon.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponType {
    EVENT("이벤트"),
    ACHIEVEMENT_CONDITION("달성조건");

    private final String title;
}
