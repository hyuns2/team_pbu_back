package projectbuildup.mivv.domain.participation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.saving_count.entity.SavingCount;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "participation")
public class Participation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "saving_count_id")
    SavingCount savingCount;

    @OneToMany(mappedBy = "participation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Remittance> remittanceList;

    public Participation(User user, Challenge challenge){
        this.user = user;
        this.challenge = challenge;
        this.savingCount = new SavingCount();
    }

    public boolean canRemit() {
        long limit = challenge.getLimitedNumberOfTimes();
        return savingCount.getCount() < limit;
    }
}
