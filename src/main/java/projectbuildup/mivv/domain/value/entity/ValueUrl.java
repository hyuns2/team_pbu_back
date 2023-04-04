package projectbuildup.mivv.domain.value.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.mivv.domain.value.dto.request.ValueRequestDto;
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValueUrl {
    private String videoUrl;
    private String imageUrl;
    private String detailImageUrl;
    private String detailBackgroundImageUrl;
    private String placeImageUrl;
    public void updateUrl(ValueRequestDto.UpdateUrlRequest requestValueDto){
        this.videoUrl = requestValueDto.getVideoUrl();
        this.imageUrl = requestValueDto.getImageUrl();
        this.detailImageUrl = requestValueDto.getDetailImageUrl();
        this.detailBackgroundImageUrl = requestValueDto.getDetailBackgroundImageUrl();
        this.placeImageUrl = requestValueDto.getPlaceImageUrl();
    }
}
