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
@Entity @Table
@NoArgsConstructor @AllArgsConstructor
@Getter @Builder
public class WorthyConsumption extends BaseTimeEntity {
    /*
    전월 달성 금액, 이용 가능 기간 추가해야 됨
    온라인인거 확인도 해야되네..
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String title;
    @ElementCollection
    @NonNull
    private List<String> hashtags = new ArrayList<>();
    @NonNull
    private int maxParticipants;
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
    @ElementCollection
    @NonNull
    private List<String> summary;
    @Nullable
    @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JoinColumn(name = "worthyConsumptionUrlId")
    @Setter
    private WorthyConsumptionUrl worthyConsumptionUrl;
    @ElementCollection
    @OneToMany(mappedBy = "worthyConsumption", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Coupon> coupons = new ArrayList<>();
    @Setter
    private LocalDate issuableCouponStartDate;
    @Setter
    private LocalDate issuableCouponEndDate;

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
    public void updateIssuableCouponDate(WorthyConsumptionRequestDto.UpdateIssuableCouponDateRequest requestWorthyConsumptionDto){
        this.issuableCouponStartDate = requestWorthyConsumptionDto.getIssuableCouponStartDate();
        this.issuableCouponEndDate = requestWorthyConsumptionDto.getIssuableCouponEndDate();
    }
    public void addCoupon(Coupon coupon){
        coupons.add(coupon);
        coupon.setWorthyConsumption(this);
    }


}
