package projectbuildup.mivv.domain.remittance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectbuildup.mivv.domain.challenge.dto.RankDto;
import projectbuildup.mivv.domain.remittance.entity.Remittance;

import java.time.LocalDateTime;
import java.util.List;


public class RemittanceDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RemitRequest{
        @Schema(hidden = true)
        private Long userId;
        @Schema(hidden = true)
        private Long challengeId;
        @Schema(description = "송금할 금액")
        private Long amount;
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
            this.title = remittance.getTitle();
            this.amount = remittance.getAmount();
            this.time = remittance.getCreatedTime();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class StatusResponse{
        long total;
        RemittanceCount remittanceCount;
        List<RankDto.ShortResponse> ranks;
    }
}
