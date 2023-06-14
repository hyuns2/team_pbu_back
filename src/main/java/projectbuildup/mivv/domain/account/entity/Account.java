package projectbuildup.mivv.domain.account.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.BaseTimeEntity;
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
@Table(name = "account")
public class Account extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "account_numbers", length = 30, nullable = false)
    String accountNumbers;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "bank_type", length = 30, nullable = false)
    BankType bankType;

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_account_to_user"))
    User user;

    @ElementCollection
    @CollectionTable(
            name = "account_connection_map",
            joinColumns = {@JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "fk_account_to_connection"))}
    )
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "bank_type", length = 30)
    @Column(name = "connection_code", length = 30, nullable = false)
    Map<OpenBanking, String> connectionMap = new HashMap<>();


    public Account(String accountNumbers, BankType bankType, OpenBanking platform, String connectionId, User user) {
        if (connectionId == null || platform == null) {
            throw new CInternalServerException();
        }
        this.accountNumbers = accountNumbers;
        this.bankType = bankType;
        this.connectionMap.put(platform, connectionId);
        this.user = user;
    }
    public void setEmptyUser(){
        this.user = null;
    }
}
