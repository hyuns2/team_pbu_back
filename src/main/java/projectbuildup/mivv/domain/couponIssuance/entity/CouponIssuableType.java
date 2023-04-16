package projectbuildup.mivv.domain.couponIssuance.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CouponIssuableType {
    UNACHIEVED_AMOUNT("전월 달성 금액 미충족 대상입니다."),
    ALREADY_ISSUED("이미 발급된 쿠폰입니다."),
    OK("발행 가능한 쿠폰입니다.");

    private final String title;
}
