package projectbuildup.mivv.domain.worthyConsumption.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionRequestDto;
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorthyConsumptionUrl {
    private String videoUrl;
    private String imageUrl;
    private String detailImageUrl;
    private String detailBackgroundImageUrl;
    private String placeImageUrl;
    public void updateUrl(WorthyConsumptionRequestDto.UpdateUrlRequest requestWorthyConsumptionDto){
        this.videoUrl = requestWorthyConsumptionDto.getVideoUrl();
        this.imageUrl = requestWorthyConsumptionDto.getImageUrl();
        this.detailImageUrl = requestWorthyConsumptionDto.getDetailImageUrl();
        this.detailBackgroundImageUrl = requestWorthyConsumptionDto.getDetailBackgroundImageUrl();
        this.placeImageUrl = requestWorthyConsumptionDto.getPlaceImageUrl();
    }
}
