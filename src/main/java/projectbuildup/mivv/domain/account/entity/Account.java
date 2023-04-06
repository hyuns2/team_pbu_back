package projectbuildup.mivv.domain.account.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String accountNumbers;
    @Enumerated(value = EnumType.STRING)
    BankType bankType;
}
