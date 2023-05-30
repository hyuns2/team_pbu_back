package projectbuildup.mivv.domain.couponIssuance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.BaseTimeEntity;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coupon_issuance")
public class CouponIssuance extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
    @Column(name = "created")
    private boolean created = false;
    @Column(name = "used")
    private boolean used = false;

    public CouponIssuance(User user, Coupon coupon) {
        this.user = user;
        this.coupon = coupon;
        this.created = true;
    }

    public void useCoupon() {
        this.used = true;
    }

}
