package projectbuildup.mivv.domain.member.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class IdentityVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String birthDate;
    String mobile;
    String code;
    @OneToOne
    @JoinColumn
    User user;
}
