package projectbuildup.mivv.domain.remittance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.remittance.dto.RemittanceDto;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.BaseTimeEntity;
import projectbuildup.mivv.global.error.exception.CIllegalArgumentException;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Remittance extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Challenge challenge;

    @Builder
    public Remittance(Challenge challenge, User user, Long amount) {
        validate(challenge, amount);
        this.challenge = challenge;
        this.user = user;
        this.amount = amount;
    }

    private void validate(Challenge challenge, Long amount){
        if (amount < challenge.getRemittanceOnceLimit()){
            throw new CIllegalArgumentException("챌린지의 최소 송금 금액 미만입니다.");
        }
    }
}
