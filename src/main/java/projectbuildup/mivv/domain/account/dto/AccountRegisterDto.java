package projectbuildup.mivv.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import projectbuildup.mivv.domain.account.entity.BankType;

@Getter
public class AccountRegisterDto {
    @Schema(description = "은행코드", example = "0004")
    BankType bankType;
    @Schema(description = "계좌번호", example = "06170204000000")
    String accountNumbers;
}
