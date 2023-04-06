package projectbuildup.mivv.domain.account.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import projectbuildup.mivv.global.error.exception.CInternalServerException;

import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String accountNumbers;
    @Enumerated(value = EnumType.STRING)
    BankType bankType;
    @ElementCollection
    Map<OpenBanking, String> connectionMap = new HashMap<>();

    @Builder
    public Account(String accountNumbers,  BankType bankType, OpenBanking platform, String connectionId){
        if (connectionId == null || platform == null){
            throw new CInternalServerException();
        }
        this.accountNumbers = accountNumbers;
        this.bankType = bankType;
        this.connectionMap.put(platform, connectionId);
    }
}
