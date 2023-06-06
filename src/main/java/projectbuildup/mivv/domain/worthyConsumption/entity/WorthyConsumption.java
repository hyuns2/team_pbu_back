package projectbuildup.mivv.domain.worthyConsumption.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.worthyConsumption.dto.WorthyConsumptionDto;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;
@Entity @Table(name = "worthy_consumption")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter @Builder
public class WorthyConsumption extends BaseTimeEntity {

    @Id @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull @Column(name = "title")
    private String title;

    @ElementCollection
    @NonNull
    @Column(name = "worthy_consumption_hashtags")
    private List<String> hashtags = new ArrayList<>();

    @NonNull
    @Column(name = "original_price")
    private int originalPrice;
    @NonNull
    @Column(name = "sale_price")
    private int salePrice;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "worthy_consumption_id")
    private List<RecommendationReason> recommendationReasons = new ArrayList<RecommendationReason>();

    @NonNull
    @Column(name = "available_price")
    private String availablePrice;
    @NonNull
    @Column(name = "available_place")
    private String availablePlace;
    @ElementCollection
    @NonNull
    @Column(name = "worthy_consumption_summary")
    private List<String> summary = new ArrayList<>();

    @Nullable
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "worthy_consumption_url_id")
    private WorthyConsumptionUrl worthyConsumptionUrl;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "condition_id")
    private Condition condition;

    //@ElementCollection @Builder.Default
    @OneToMany(mappedBy = "worthyConsumption", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Coupon> coupons = new ArrayList<Coupon>();


    public void update(WorthyConsumptionDto.Update worthyConsumptionDto){
        this.title = worthyConsumptionDto.getTitle();
        this.hashtags = worthyConsumptionDto.getHashtags();
        this.recommendationReasons = worthyConsumptionDto.getRecommendationReasons();
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
