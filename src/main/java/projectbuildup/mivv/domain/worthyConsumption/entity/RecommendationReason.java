package projectbuildup.mivv.domain.worthyConsumption.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "recommendation_reason")
public class RecommendationReason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Setter
    @JoinColumn(name = "worthy_consumption_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private WorthyConsumption worthyConsumption;

    public RecommendationReason(String title, String description, WorthyConsumption worthyConsumption) {
        this.title = title;
        this.description = description;
        this.worthyConsumption = worthyConsumption;
    }
}
