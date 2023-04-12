package projectbuildup.mivv.domain.couponIssuance.entity;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlID;
import lombok.Builder;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.BaseTimeEntity;
@Entity
@Builder

public class CouponIssuance extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Coupon coupon;
    private Boolean isCreated;
    private Boolean isUsed;
    public CouponIssuance(User user, Coupon coupon){
        this.user = user;
        this.coupon = coupon;
    }

}
