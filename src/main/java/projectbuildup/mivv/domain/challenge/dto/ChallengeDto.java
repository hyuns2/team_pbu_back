package projectbuildup.mivv.domain.challenge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.global.common.imageStore.Image;

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

        @Schema(description = "최대 송금액")
        @Min(1000)
        private long maxSavingAmount;
        @Schema(description = "최소 송금액")
        @Min(1000)
        private long minSavingAmount;
        @Schema(description = "하루 송금 횟수 제한")
        @Min(1)
        private int limitedNumberOfTimes;
        @Schema(description = "시작일자", example = "yyyy-MM-dd")
        @FutureOrPresent
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate startDate;
        @Schema(description = "종료일자", example = "yyyy-MM-dd")
        @Future
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate endDate;
        @Schema(description = "이미지")
        @NotNull
        MultipartFile imageFile;
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

        @Schema(description = "이미지")
        @NotNull
        MultipartFile imageFile;

        public void setChallengeId(Long challengeId) {
            this.challengeId = challengeId;
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
        private long limitedNumberOfTimes;

        private long maxSavingAmount;
        private long minSavingAmount;
        private long numOfParticipation;
        private Image image;
        private long totalAmount;

        public Response(Challenge challenge, long totalAmount) {
            this.id = challenge.getId();
            this.startDate = challenge.getStartDate();
            this.endDate = challenge.getEndDate();
            this.mainTitle = challenge.getMainTitle();
            this.subTitle = challenge.getSubTitle();
            this.description = challenge.getDescription();
            this.limitedNumberOfTimes = challenge.getLimitedNumberOfTimes();
            this.maxSavingAmount = challenge.getMaxSavingAmount();
            this.minSavingAmount = challenge.getMinSavingAmount();
            this.numOfParticipation = challenge.getParticipationList().size();
            this.image = challenge.getImage();
            this.totalAmount = totalAmount;
        }
    }
}
