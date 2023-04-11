package projectbuildup.mivv.domain.worthyConsumption.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionRequestDto;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.util.List;
@Entity @Table
@NoArgsConstructor @AllArgsConstructor
@Getter @Builder
public class WorthyConsumption extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private List<String> hashtags;
    @NonNull
    private int maxParticipants;
    @NonNull
    private int originalPrice;
    @NonNull
    private int salePrice;
    @NonNull
    private List<String> whyRecommendation;
    @NonNull
    private String priceTag;
    @NonNull
    private String placeTag;
    @NonNull
    private List<String> summary;
    @Nullable
    @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JoinColumn(name = "worthyConsumptionUrl_id")
    @Setter
    private WorthyConsumptionUrl worthyConsumptionUrl;
    @OneToMany(mappedBy = "worthyConsumption", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Coupon> coupons;

    public void updateContent(WorthyConsumptionRequestDto.UpdateContentRequest requestWorthyConsumptionDto){
        this.title = requestWorthyConsumptionDto.getTitle();
        this.hashtags = requestWorthyConsumptionDto.getHashtags();
        this.maxParticipants = requestWorthyConsumptionDto.getMaxParticipants();
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
        coupons.add(coupon);
        coupon.setWorthyConsumption(this);
    }


}
