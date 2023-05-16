package projectbuildup.mivv.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AccountRegisterDto {
    @NotNull
    @Schema(description = "은행코드", example = "0004")
    String organizationCode;
    @NotNull
    @Schema(description = "계좌번호", example = "06170204000000")
    String accountNumbers;
    @NotNull
    @Schema(description = "은행 아이디")
    String bankId;
    @NotNull
    @Schema(description = "은행 비밀번호")
    String bankPassword;
    @Schema(description = "출금 계좌 비밀번호 (대구은행인 경우)")
    String accountPassword;
}
