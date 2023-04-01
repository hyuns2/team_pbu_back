package projectbuildup.mivv.domain.coupon.entity;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.time.LocalDate;
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon extends BaseTimeEntity {
    @Id
    Long id;
    @Nullable
    Long valueId;
    @Nonnull
    String title;
    @Nonnull
    String content;
    @Nonnull
    String imageUrl;
    int pin;
    Boolean create;
    Boolean use;
    LocalDate limitDate;
    int originalPrice;
    int salePrice;


}
