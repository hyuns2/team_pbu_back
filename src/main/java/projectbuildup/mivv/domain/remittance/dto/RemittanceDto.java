package projectbuildup.mivv.domain.remittance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class RemittanceDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RemitRequest{
        @Schema(hidden = true)
        private Long userId;
        @Schema(hidden = true)
        private Long challengeId;
        @Schema(description = "송금할 금액")
        private Long amount;

        public void setUserId(Long userId) {
            this.userId = userId;
        }
        public void setChallengeId(Long challengeId){
            this.challengeId = challengeId;
        }
    }
}
