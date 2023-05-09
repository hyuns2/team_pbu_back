package projectbuildup.mivv.domain.worthyConsumption.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionRequestDto;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.time.LocalDate;
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
    private List<String> whyRecommendation = new ArrayList<>();

    @NonNull
    private String priceTag;
    @NonNull
    private String placeTag;
    @NonNull
    private String summary;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JoinColumn(name = "WorthyConsumptionUrlId")
    @Setter
    private WorthyConsumptionUrl worthyConsumptionUrl;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JoinColumn(name = "ConditionId")
    @Setter
    private Condition condition;

    //@ElementCollection @Builder.Default
    @OneToMany(mappedBy = "worthyConsumption", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Coupon> coupons = new ArrayList<Coupon>();

    public void updateContent(WorthyConsumptionRequestDto.UpdateContentRequest requestWorthyConsumptionDto){
        this.title = requestWorthyConsumptionDto.getTitle();
        this.hashtags = requestWorthyConsumptionDto.getHashtags();
        this.whyRecommendation = requestWorthyConsumptionDto.getWhyRecommendation();
        this.summary = requestWorthyConsumptionDto.getSummary();
    }

    public void updatePrice(WorthyConsumptionRequestDto.UpdatePriceRequest requestWorthyConsumptionDto){
        this.originalPrice = requestWorthyConsumptionDto.getOriginalPrice();
        this.salePrice = requestWorthyConsumptionDto.getSalePrice();
        this.priceTag = requestWorthyConsumptionDto.getPriceTag();
    }
    public void updatePlace(WorthyConsumptionRequestDto.UpdatePlaceRequest requestWorthyConsumptionDto){
        this.placeTag = requestWorthyConsumptionDto.getPlaceTag();
    }

    public void addCoupon(Coupon coupon){
        coupon.setWorthyConsumption(this);
        coupon.getWorthyConsumption().getCoupons().add(coupon);
        //coupons.add(coupon);
        //coupon.setWorthyConsumption(this);
    }


}
