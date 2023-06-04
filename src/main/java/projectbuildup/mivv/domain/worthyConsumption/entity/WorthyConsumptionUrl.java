package projectbuildup.mivv.domain.worthyConsumption.entity;

import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.worthyConsumption.dto.WorthyConsumptionDto;

@Entity @Table(name = "worthy_consumption_url")
@AllArgsConstructor @NoArgsConstructor
@Getter @Builder
public class WorthyConsumptionUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "logo_path")
    private String logoPath;
    @Column(name = "video_path")
    private String videoPath;
    @Column(name = "video_thumbnail_path")
    private String videoThumbNailPath;
    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "detail_image_path")
    private String detailImagePath;
    @Column(name = "detail_background_image_path")
    private String detailBackgroundImagePath;
    @Column(name = "place_image_path")
    private String placeImagePath;
    public WorthyConsumptionUrl(String logoPath, String videoPath, String videoThumbNailPath,
                                String imagePath, String detailImagePath, String detailBackgroundImagePath, String placeImagePath) {
        this.logoPath = logoPath;
        this.videoThumbNailPath = videoThumbNailPath;
        this.videoPath = videoPath;
        this.imagePath = imagePath;
        this.detailImagePath = detailImagePath;
        this.detailBackgroundImagePath = detailBackgroundImagePath;
        this.placeImagePath = placeImagePath;
    }

    public void update(String logoPath, String videoPath, String videoThumbNailPath,
                          String imagePath, String detailImagePath, String detailBackgroundImagePath, String placeImagePath){
        this.logoPath = logoPath;
        this.videoThumbNailPath = videoThumbNailPath;
        this.videoPath = videoPath;
        this.imagePath = imagePath;
        this.detailImagePath = detailImagePath;
        this.detailBackgroundImagePath = detailBackgroundImagePath;
        this.placeImagePath = placeImagePath;
    }
}
