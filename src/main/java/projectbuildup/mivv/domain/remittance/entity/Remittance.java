package projectbuildup.mivv.domain.remittance.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.global.common.BaseTimeEntity;
import projectbuildup.mivv.global.error.exception.CBadRequestException;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE remittance SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@Table(name = "remittance")
public class Remittance extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "amount")
    private long amount;
    @Column(name = "title")
    private String title;
    @ManyToOne
    @JoinColumn(name = "participation_id")
    Participation participation;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

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

    public static Remittance newWithdrawal(long amount, Participation participation) {
        if (amount > 0) {
            throw new CBadRequestException("출금액은 양수일 수 없습니다.");
        }
        return new Remittance(amount, participation, "출금");
    }
}
