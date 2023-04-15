package projectbuildup.mivv.domain.challenge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import projectbuildup.mivv.domain.challenge.entity.Challenge;

import java.time.LocalDate;
import java.util.List;

public class ChallengeDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreationRequest {
        @Schema(description = "큰제목")
        @NotBlank
        String mainTitle;
        @Schema(description = "제목")
        @NotBlank
        String subTitle;
        @Schema(description = "소개")
        @NotNull
        List<String> description;

        @Schema(description = "1회 송금액")
        @Min(1000)
        private int remittanceOnceLimit;

        @Schema(description = "하루 송금 횟수 제한")
        @Min(1)
        private int remittanceAvailableCount;

        @Schema(description = "이미지 URL")
        @NotBlank
        private String imageUrl;

        @Schema(description = "시작일자", example = "yyyy-MM-dd")
        @FutureOrPresent
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate startDate;

        @Schema(description = "종료일자", example = "yyyy-MM-dd")
        @Future
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate endDate;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateRequest {
        @Schema(hidden = true)
        Long challengeId;
        @Schema(description = "큰제목")
        @NotBlank
        String mainTitle;
        @Schema(description = "제목")
        @NotBlank
        String subTitle;
        @Schema(description = "소개")
        @NotNull
        List<String> description;

        @Schema(description = "1회 송금액")
        @Min(1000)
        private long remittanceOnceLimit;

        @Schema(description = "하루 송금 횟수 제한")
        @Min(1)
        private long remittanceAvailableCount;

        @Schema(description = "이미지 URL")
        @NotBlank
        private String imageUrl;

        public void setChallengeId(Long challengeId) {
            this.challengeId = challengeId;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShortResponse {
        private Long id;
        private LocalDate endDate;
        private String mainTitle;
        private String subTitle;
        private long remittanceOnceLimit;

        public ShortResponse(Challenge challenge) {
            this.id = challenge.getId();
            this.endDate = challenge.getEndDate();
            this.mainTitle = challenge.getMainTitle();
            this.subTitle = challenge.getSubTitle();
            this.remittanceOnceLimit = challenge.getRemittanceOnceLimit();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private LocalDate startDate;
        private LocalDate endDate;
        private String mainTitle;
        private String subTitle;
        private List<String> description;
        private long remittanceOnceLimit;
        private long remittanceAvailableCount;

        public Response(Challenge challenge) {
            this.id = challenge.getId();
            this.startDate = challenge.getStartDate();
            this.endDate = challenge.getEndDate();
            this.mainTitle = challenge.getMainTitle();
            this.subTitle = challenge.getSubTitle();
            this.description = challenge.getDescription();
            this.remittanceOnceLimit = challenge.getRemittanceOnceLimit();
            this.remittanceAvailableCount = challenge.getRemittanceAvailableCount();
        }
    }
}
