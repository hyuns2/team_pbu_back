package projectbuildup.mivv.domain.participation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE participation SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@Table(name = "participation")
public class Participation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_participation_to_user"), nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "challenge_id", foreignKey = @ForeignKey(name = "fk_participation_to_challenge"), nullable = false)
    private Challenge challenge;

    @Column(name = "saving_count_of_day")
    long savingCountOfDay;

    @OneToMany(mappedBy = "participation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Remittance> remittanceList;

    @Column(name = "closed")
    private boolean closed;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Participation(User user, Challenge challenge) {
        this.user = user;
        this.challenge = challenge;
        this.savingCountOfDay = 0L;
    }

    public boolean canRemit() {
        long limit = challenge.getLimitedNumberOfTimes();
        return this.savingCountOfDay < limit;
    }

    public void addCount() {
        this.savingCountOfDay++;
    }

    public void initialize() {
        this.savingCountOfDay = 0;
    }

    public void close(){
        this.closed = true;
    }
}
