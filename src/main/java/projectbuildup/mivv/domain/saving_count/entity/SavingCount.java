package projectbuildup.mivv.domain.saving_count.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.user.entity.User;

@Getter
@Entity
public class SavingCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int count;
    @OneToOne(mappedBy = "savingCount", fetch = FetchType.LAZY)
    Participation participation;
    public SavingCount(){
        this.count = 0;
    }
    public void addCount(){
        this.count++;
    }
    public void initialize(){
        this.count = 0;
    }
}
