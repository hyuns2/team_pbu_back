package projectbuildup.mivv.domain.account.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Columns;
import projectbuildup.mivv.global.error.exception.CInternalServerException;

import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "account_numbers")
    String accountNumbers;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "bank_type")
    BankType bankType;
    @ElementCollection
    @CollectionTable(
            name = "account_connection_map",
            joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")}
    )
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "bank_type")
    @Column(name = "connection_code")
    Map<OpenBanking, String> connectionMap = new HashMap<>();

    public Account(String accountNumbers, BankType bankType, OpenBanking platform, String connectionId) {
        if (connectionId == null || platform == null) {
            throw new CInternalServerException();
        }
        this.accountNumbers = accountNumbers;
        this.bankType = bankType;
        this.connectionMap.put(platform, connectionId);
    }
}
