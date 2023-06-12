package projectbuildup.mivv.domain.coupon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import projectbuildup.mivv.domain.coupon.dto.CouponDto;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "coupon")
@Getter
@AllArgsConstructor @NoArgsConstructor(force = true)
@Builder
public class Coupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worthy_consumption_id")
    @Setter
    private WorthyConsumption worthyConsumption;
    @Column(name = "title", nullable = false, length = 30)
    private String title;
    @Column(name = "image_path", nullable = false)
    private String imagePath;
    @Column(name = "pin", nullable = false, length = 10)
    private int pin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "issuable_start_date")
    private LocalDate issuableStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "issuable_end_date")
    private LocalDate issuableEndDate;
    @Column(name = "limit_start_date", nullable = false)
    private LocalDate limitStartDate;
    @Column(name = "limit_end_date", nullable = false)
    private LocalDate limitEndDate;
    @ElementCollection
    @NonNull
    @Column(name = "coupon_summary")
    private List<String> summary = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_type")
    private CouponType couponType;
    @Column(name = "issue_count_limit", nullable = false)
    private Boolean issueCountLimit;
    @NonNull
    @Column(name = "how_to_use", nullable = false)
    private String howToUse;
    @ElementCollection
    @NonNull
    @Column(name = "coupon_caution")
    private List<String> caution;
    @NonNull
    @Column(name = "price_tag")
    private String priceTag;
    @NonNull
    @Column(name = "available_price")
    private long availablePrice;

    public static Coupon toEntity(CouponDto.Request couponDto, String imagePath){
        return Coupon.builder()
                .title(couponDto.getTitle())
                .imagePath(imagePath)
                .pin(couponDto.getPin())
                .issuableStartDate(couponDto.getIssuableStartDate())
                .issuableEndDate(couponDto.getIssuableEndDate())
                .limitStartDate(couponDto.getLimitStartDate())
                .limitEndDate(couponDto.getLimitEndDate())
                .summary(couponDto.getSummary())
                .couponType(couponDto.getCouponType())
                .issueCountLimit(couponDto.getIssueCountLimit())
                .howToUse(couponDto.getHowToUse())
                .caution(couponDto.getCaution())
                .priceTag(couponDto.getPriceTag())
                .availablePrice(couponDto.getAvailablePrice())
                .build();
    }
    public void update(CouponDto.Request couponDto, String imagePath){
        this.title = couponDto.getTitle();
        this.imagePath = imagePath;
        this.pin = couponDto.getPin();
        this.limitStartDate = couponDto.getLimitStartDate();
        this.limitEndDate = couponDto.getLimitEndDate();
        this.issuableStartDate = couponDto.getIssuableStartDate();
        this.issuableEndDate = couponDto.getIssuableEndDate();
        this.summary = couponDto.getSummary();
        this.couponType = couponDto.getCouponType();
        this.issueCountLimit = couponDto.getIssueCountLimit();
        this.howToUse = couponDto.getHowToUse();
        this.caution = couponDto.getCaution();
        this.priceTag = couponDto.getPriceTag();
        this.availablePrice = couponDto.getAvailablePrice();
    }


}
