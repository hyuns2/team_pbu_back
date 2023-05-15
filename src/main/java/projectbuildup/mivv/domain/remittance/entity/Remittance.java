package projectbuildup.mivv.domain.remittance.entity;

import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.global.common.BaseTimeEntity;
import projectbuildup.mivv.global.error.exception.CBadRequestException;

@Entity
@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor
public class Remittance extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long amount;

    private String title;
    @ManyToOne
    @JoinColumn
    Participation participation;

    public Remittance(long amount, Participation participation) {
        this.amount = amount;
        this.title = participation.getChallenge().getMainTitle();
        this.participation = participation;
    }

    public Remittance(long amount, Participation participation, String title) {
        this.amount = amount;
        this.title = title;
        this.participation = participation;
    }

    public static Remittance newDeposit(long amount, Participation participation) {
        if (amount < participation.getChallenge().getMaxSavingAmount()) {
            throw new CBadRequestException("최소 금액 이상 송금할 수 있습니다.");
        }
        return new Remittance(amount, participation);
    }
    public static Remittance newWithdrawal(long amount, Participation participation){
        if (amount > 0 ){
            throw new CBadRequestException("출금액은 양수일 수 없습니다.");
        }
        return new Remittance(amount, participation, "출금");
   }
}
