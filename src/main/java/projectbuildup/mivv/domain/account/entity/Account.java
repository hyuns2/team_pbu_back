package projectbuildup.mivv.domain.account.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import projectbuildup.mivv.global.error.exception.CInternalServerException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE account SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "account_numbers", length = 30, nullable = false)
    String accountNumbers;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "bank_type", length = 30, nullable = false)
    BankType bankType;

    @ElementCollection
    @CollectionTable(
            name = "account_connection_map",
            joinColumns = {@JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "fk_account_to_connection"))}
    )
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "bank_type", length = 30)
    @Column(name = "connection_code", length = 30, nullable = false)
    Map<OpenBanking, String> connectionMap = new HashMap<>();

    @Column(name = "deleted_at")
    LocalDateTime deletedAt;

    public Account(String accountNumbers, BankType bankType, OpenBanking platform, String connectionId) {
        if (connectionId == null || platform == null) {
            throw new CInternalServerException();
        }
        this.accountNumbers = accountNumbers;
        this.bankType = bankType;
        this.connectionMap.put(platform, connectionId);
    }
}
