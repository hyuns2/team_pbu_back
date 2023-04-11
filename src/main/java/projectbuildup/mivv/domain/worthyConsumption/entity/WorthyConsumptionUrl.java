package projectbuildup.mivv.domain.worthyConsumption.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionRequestDto;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionUrlRequestDto;

@Entity @Table(name = "WorthyConsumption_URL")
@AllArgsConstructor @NoArgsConstructor
@Getter @Builder
public class WorthyConsumptionUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worthyConsumptionUrl_id")
    private Long id;
    private String videoUrl;
    private String imageUrl;
    private String detailImageUrl;
    private String detailBackgroundImageUrl;
    private String placeImageUrl;
    //@MapsId
    //@Nullable
    @OneToOne(mappedBy = "worthyConsumptionUrl", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private WorthyConsumption worthyConsumption;

    public WorthyConsumptionUrl(WorthyConsumptionUrlRequestDto.CreationRequest worthyConsumptionUrlRequest) {
        this.videoUrl = worthyConsumptionUrlRequest.getVideoUrl();
        this.imageUrl = worthyConsumptionUrlRequest.getImageUrl();
        this.detailImageUrl = worthyConsumptionUrlRequest.getDetailImageUrl();
        this.detailBackgroundImageUrl = worthyConsumptionUrlRequest.getDetailBackgroundImageUrl();
        this.placeImageUrl = worthyConsumptionUrlRequest.getPlaceImageUrl();
    }

    public void updateUrl(WorthyConsumptionRequestDto.UpdateUrlRequest requestWorthyConsumptionDto){
        this.videoUrl = requestWorthyConsumptionDto.getVideoUrl();
        this.imageUrl = requestWorthyConsumptionDto.getImageUrl();
        this.detailImageUrl = requestWorthyConsumptionDto.getDetailImageUrl();
        this.detailBackgroundImageUrl = requestWorthyConsumptionDto.getDetailBackgroundImageUrl();
        this.placeImageUrl = requestWorthyConsumptionDto.getPlaceImageUrl();
    }
}
