package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@Entity
@DiscriminatorValue("CouponCondition")
@Table(name = "CouponConditionCard")
public class CouponConditionCardEntity extends CardEntity {

    // 몇번째 발급자인지
    protected Integer whatNumber;

    // 몇번 이상 연속 발급 성공자인지
    protected Integer howSuccessive;

}
