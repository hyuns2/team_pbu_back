package projectbuildup.mivv.domain.couponIssuance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.BaseTimeEntity;

@Entity @Builder
@Getter
@AllArgsConstructor @NoArgsConstructor
public class CouponIssuance extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    private Coupon coupon;

    private boolean created = false;
    private boolean used = false;
    public CouponIssuance(User user, Coupon coupon){
        this.user = user;
        this.coupon = coupon;
        this.created = true;
    }
    public void useCoupon(){
        this.used = true;
    }

}
