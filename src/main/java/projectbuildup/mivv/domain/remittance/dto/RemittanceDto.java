package projectbuildup.mivv.domain.remittance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.global.constant.ExampleValue;

import java.time.LocalDateTime;


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

    @Getter
    public static class DetailsResponse {
        @Schema(description = "내용")
        String title;
        @Schema(description = "금액")
        long amount;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "생성 시간")
        LocalDateTime time;

        public DetailsResponse(Remittance remittance) {
            this.title = remittance.getChallenge().getMainTitle();
            this.amount = remittance.getAmount();
            this.time = remittance.getCreatedTime();
        }
    }
}
