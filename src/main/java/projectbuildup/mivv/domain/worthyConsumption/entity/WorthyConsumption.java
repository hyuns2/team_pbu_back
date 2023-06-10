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
    @Setter
    @OneToMany(mappedBy = "worthyConsumption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecommendationReason> recommendationReasons = new ArrayList<RecommendationReason>();
    @NonNull
    @Column(name = "available_place")
    private String availablePlace;
    @NonNull
    @Column(name = "available_place_detail")
    private String availablePlaceDetail;
    @Nullable
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "worthy_consumption_url_id")
    private WorthyConsumptionUrl worthyConsumptionUrl;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "condition_id")
    private Condition condition;

    //@ElementCollection @Builder.Default
    @OneToMany(mappedBy = "worthyConsumption", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Coupon> coupons = new ArrayList<Coupon>();


    public void update(WorthyConsumptionDto.Request worthyConsumptionDto, List<RecommendationReason> recommendationReasons){
        this.title = worthyConsumptionDto.getTitle();
        this.hashtags = worthyConsumptionDto.getHashtags();
        this.originalPrice = worthyConsumptionDto.getOriginalPrice();
        this.salePrice = worthyConsumptionDto.getSalePrice();
        this.availablePlace = worthyConsumptionDto.getAvailablePlace();
        this.availablePlaceDetail = worthyConsumptionDto.getAvailablePlaceDetail();
        this.recommendationReasons = recommendationReasons;
    }
    public void addCoupon(Coupon coupon){
        coupon.setWorthyConsumption(this);
        coupon.getWorthyConsumption().getCoupons().add(coupon);
        //coupons.add(coupon);
        //coupon.setWorthyConsumption(this);
    }
    public void addRR(RecommendationReason recommendationReason){
        recommendationReason.setWorthyConsumption(this);
        recommendationReason.getWorthyConsumption().getRecommendationReasons().add(recommendationReason);
    }
    public void deleteRR(){
        this.recommendationReasons.clear();
    }
}
