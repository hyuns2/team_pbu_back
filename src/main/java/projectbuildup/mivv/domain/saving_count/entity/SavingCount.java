package projectbuildup.mivv.domain.saving_count.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.BaseTimeEntity;

@Getter
@Entity
@Table(name = "saving_count")
public class SavingCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "count", nullable = false)
    int count;

    public SavingCount() {
        this.count = 0;
    }

    public void addCount() {
        this.count++;
    }

    public void initialize() {
        this.count = 0;
    }
}
