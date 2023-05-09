package projectbuildup.mivv.domain.worthyConsumption.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.worthyConsumption.dto.WorthyConsumptionDto;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;
@Entity @Table(name = "WorthyConsumption")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter @Builder
public class WorthyConsumption extends BaseTimeEntity {

    @Id @Column(name = "WorthyConsumptionId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull @Column(name = "WorthyConsumptionTitle")
    private String title;

    @ElementCollection
    @NonNull
    private List<String> hashtags = new ArrayList<>();

    @NonNull
    private int originalPrice;
    @NonNull
    private int salePrice;

    @ElementCollection
    @NonNull
    private List<String> recommendationReason = new ArrayList<>();

    @NonNull
    private String availablePrice;
    @NonNull
    private String availablePlace;
    @NonNull
    private String summary;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "WorthyConsumptionUrlId")
    @Setter
    private WorthyConsumptionUrl worthyConsumptionUrl;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "ConditionId")
    @Setter
    private Condition condition;

    //@ElementCollection @Builder.Default
    @OneToMany(mappedBy = "worthyConsumption", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Coupon> coupons = new ArrayList<Coupon>();


    public void update(WorthyConsumptionDto.Update worthyConsumptionDto){
        this.title = worthyConsumptionDto.getTitle();
        this.hashtags = worthyConsumptionDto.getHashtags();
        this.recommendationReason = worthyConsumptionDto.getWhyRecommendation();
        this.summary = worthyConsumptionDto.getSummary();
        this.originalPrice = worthyConsumptionDto.getOriginalPrice();
        this.salePrice = worthyConsumptionDto.getSalePrice();
        this.availablePrice = worthyConsumptionDto.getAvailablePrice();
        this.availablePlace = worthyConsumptionDto.getAvailablePlace();
    }

    public void addCoupon(Coupon coupon){
        coupon.setWorthyConsumption(this);
        coupon.getWorthyConsumption().getCoupons().add(coupon);
        //coupons.add(coupon);
        //coupon.setWorthyConsumption(this);
    }


}
