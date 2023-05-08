package projectbuildup.mivv.domain.worthyConsumption.entity;

import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionRequestDto;
import projectbuildup.mivv.domain.worthyConsumption.dto.request.WorthyConsumptionUrlDto;

@Entity @Table(name = "WorthyConsumptionURL")
@AllArgsConstructor @NoArgsConstructor
@Getter @Builder
public class WorthyConsumptionUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorthyConsumptionUrlId")
    private Long id;
    private String videoPath;
    private String imagePath;
    private String detailImagePath;
    private String detailBackgroundImagePath;
    private String placeImagePath;
    //@MapsId
    //@Nullable
    //@OneToOne(mappedBy = "worthyConsumptionUrl", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private WorthyConsumption worthyConsumption;

    /*public WorthyConsumptionUrl(WorthyConsumptionUrlDto.CreationRequest worthyConsumptionUrlRequest,
                                String imagePath, String detailImagePath, String detailBackgroundImagePath, String placeImagePath) {
        this.videoPath = worthyConsumptionUrlRequest.getVideoPath();
        this.imagePath = imagePath;
        this.detailImagePath = detailImagePath;
        this.detailBackgroundImagePath = detailBackgroundImagePath;
        this.placeImagePath = placeImagePath;
    }

    public void updateUrl(WorthyConsumptionRequestDto.UpdateUrlRequest requestWorthyConsumptionDto,
                          String imagePath, String detailImagePath, String detailBackgroundImagePath, String placeImagePath){
        this.videoPath = requestWorthyConsumptionDto.getVideoPath();
        this.imagePath = imagePath;
        this.detailImagePath = detailImagePath;
        this.detailBackgroundImagePath = detailBackgroundImagePath;
        this.placeImagePath = placeImagePath;
    }*/
    public WorthyConsumptionUrl(WorthyConsumptionRequestDto.CreationRequest worthyConsumptionRequestDto,
                                String imagePath, String detailImagePath, String detailBackgroundImagePath, String placeImagePath) {
        this.videoPath = worthyConsumptionRequestDto.getVideoPath();
        this.imagePath = imagePath;
        this.detailImagePath = detailImagePath;
        this.detailBackgroundImagePath = detailBackgroundImagePath;
        this.placeImagePath = placeImagePath;
    }

    public void updateUrl(WorthyConsumptionRequestDto.UpdateUrlRequest requestWorthyConsumptionDto,
                          String imagePath, String detailImagePath, String detailBackgroundImagePath, String placeImagePath){
        this.videoPath = requestWorthyConsumptionDto.getVideoPath();
        this.imagePath = imagePath;
        this.detailImagePath = detailImagePath;
        this.detailBackgroundImagePath = detailBackgroundImagePath;
        this.placeImagePath = placeImagePath;
    }
}
