package projectbuildup.mivv.domain.value.entity;

import jakarta.persistence.Entity;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.util.List;
@Entity
public class Value extends BaseTimeEntity {
    Long id;
    String title;
    List<String> hashtags;
    int maxParticipants;
    String videoUrl;
    String imageUrl;
    String detailImageUrl;
    String detailBackgroundImageUrl;
    int originalPrice;
    int salePrice;
    List<String> whyRecommendation;
    String priceTag;
    String placeImageUrl;
    List<String> summary;


}
