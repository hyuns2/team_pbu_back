package projectbuildup.mivv.domain.remittance.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.BaseTimeEntity;
import projectbuildup.mivv.global.error.exception.CIllegalArgumentException;

@Getter
@NoArgsConstructor
@Entity
@ToString
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
        this.challenge = challenge;
        this.user = user;
        this.amount = amount;
    }
}
