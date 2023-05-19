package projectbuildup.mivv.domain.worthyConsumption.entity;

import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.worthyConsumption.dto.WorthyConsumptionDto;

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
    public WorthyConsumptionUrl(String videoPath,
                                String imagePath, String detailImagePath, String detailBackgroundImagePath, String placeImagePath) {
        this.videoPath = videoPath;
        this.imagePath = imagePath;
        this.detailImagePath = detailImagePath;
        this.detailBackgroundImagePath = detailBackgroundImagePath;
        this.placeImagePath = placeImagePath;
    }

    public void update(String videoPath,
                          String imagePath, String detailImagePath, String detailBackgroundImagePath, String placeImagePath){
        this.videoPath = videoPath;
        this.imagePath = imagePath;
        this.detailImagePath = detailImagePath;
        this.detailBackgroundImagePath = detailBackgroundImagePath;
        this.placeImagePath = placeImagePath;
    }
}
