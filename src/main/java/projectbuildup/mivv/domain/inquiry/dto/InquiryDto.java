package projectbuildup.mivv.domain.inquiry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import projectbuildup.mivv.domain.inquiry.entity.InquiryEntity;
import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDateTime;

public class InquiryDto {

    @AllArgsConstructor
    @Data
    public static class InquiryRequestDto {

        @NotBlank
        @Length(min = 2, max = 30)
        @Schema(description = "문의 제목")
        private String title;

        @NotBlank
        @Length(min = 2, max = 1000)
        @Schema(description = "문의 내용")
        private String content;

        public static InquiryEntity toEntity(final InquiryRequestDto irDto, final User user) {
            LocalDateTime localDateTime = LocalDateTime.now();

            return InquiryEntity.builder().
                    user(user).
                    title(irDto.getTitle()).
                    content(irDto.getContent()).
                    answer(null).
                    timeStamp(localDateTime).build();

        }
    }

    @AllArgsConstructor
    @Data
    public static class AnswerRequestDto {

        @NotNull
        @Schema(description = "문의 고유번호")
        private Long id;

        @NotBlank
        @Length(min = 2, max = 1000)
        @Schema(description = "답변 내용")
        private String answer;

    }

    @AllArgsConstructor
    @Data
    public static class SimpleRequestDto {
        @NotBlank
        @Schema(description = "문의 고유번호")
        private Long id;

    }

    @AllArgsConstructor
    @Data
    public static class InquiryResponseDto {
        @Schema(description = "문의 고유번호")
        private Long id;

        @Schema(description = "문의 제목")
        private String title;

        @Schema(description = "문의 내용")
        private String content;

        @Schema(description = "답변 내용")
        private String answer;

        @Schema(description = "문의 시간")
        private LocalDateTime timeStamp;

        public InquiryResponseDto(final InquiryEntity entity) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.answer = entity.getAnswer();
            this.timeStamp = entity.getTimeStamp();
        }
    }

}
