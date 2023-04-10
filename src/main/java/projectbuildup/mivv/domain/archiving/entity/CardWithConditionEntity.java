package projectbuildup.mivv.domain.archiving.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

public class CardWithConditionEntity {

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @Entity
    @DiscriminatorValue("NumericalCondition")
    @Table(name = "NumericalConditionCard")
    public static class NumericalConditionCardEntity extends CardEntity {

        protected int charge;
        protected int count;
        protected int term;

        @Builder
        public NumericalConditionCardEntity(String kind, String title, String subTitle, String sentence, String image, int charge, int count, int term) {
            super(kind, title, subTitle, sentence, image);
            this.charge = charge;
            this.count = count;
            this.term = term;
        }

    }

}
