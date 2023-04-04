package projectbuildup.mivv.domain.value.entity;

import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.value.dto.request.ValueRequestDto;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.util.List;
@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Value extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private List<String> hashtags;
    @NonNull
    private int maxParticipants;
    private int originalPrice;
    private int salePrice;
    private List<String> whyRecommendation;
    private String priceTag;
    private String placeTag;
    private List<String> summary;
    @OneToOne(mappedBy = "Value")
    private ValueUrl valueUrl;

    public void updateContent(ValueRequestDto.UpdateContentRequest requestValueDto){
        this.title = requestValueDto.getTitle();
        this.hashtags = requestValueDto.getHashtags();
        this.maxParticipants = requestValueDto.getMaxParticipants();
        this.whyRecommendation = requestValueDto.getWhyRecommendation();
        this.summary = requestValueDto.getSummary();
    }

    public void updatePrice(ValueRequestDto.UpdatePriceRequest requestValueDto){
        this.originalPrice = requestValueDto.getOriginalPrice();
        this.salePrice = requestValueDto.getSalePrice();
        this.priceTag = requestValueDto.getPriceTag();
    }
    public void updatePlace(ValueRequestDto.UpdatePlaceRequest requestValueDto){
        this.placeTag = requestValueDto.getPlaceTag();
    }


}
