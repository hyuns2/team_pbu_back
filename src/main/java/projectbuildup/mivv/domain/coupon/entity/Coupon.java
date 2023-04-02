package projectbuildup.mivv.domain.coupon.entity;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Nullable
    Long valueId;
    @Nonnull
    String title;
    @Nonnull
    String content;
    @Nonnull
    String imageUrl;
    @Nonnull
    int pin;
    Boolean create = false;
    Boolean use = false;
    @Nonnull
    LocalDate limitDate;
    @Nonnull
    int originalPrice;
    @Nonnull
    int salePrice;

    public boolean isCreated(){
        return create;
    }
    public boolean isUsed(){
        return use;
    }


}
