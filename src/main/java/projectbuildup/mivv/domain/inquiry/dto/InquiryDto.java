package projectbuildup.mivv.domain.inquiry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import projectbuildup.mivv.domain.inquiry.entity.InquiryEntity;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@NoArgsConstructor
public class InquiryDto {

    @Builder
    @AllArgsConstructor
    @Data
    public static class InquiryRegisterDto {
        @Length(min = 2, max = 30)
        @Schema(description = "문의 제목")
        private String title;

        @Length(min = 2, max = 1000)
        @Schema(description = "문의 내용")
        private String content;

        public static InquiryEntity toEntity(final InquiryRegisterDto irDto) {
            LocalDateTime localDateTime = LocalDateTime.now();

            return InquiryEntity.builder().
                    title(irDto.getTitle()).
                    content(irDto.getContent()).
                    answer(null).
                    timeStamp(localDateTime).build();

        }
    }

    @Builder
    @AllArgsConstructor
    @Data
    public static class AnswerRequestDto {
        @NotBlank
        @Schema(description = "문의 고유번호")
        private Long id;
        @Length(min = 2, max = 1000)
        @Schema(description = "답변 내용")
        private String answer;

        public static InquiryEntity toEntity(final AnswerRequestDto arDto) {

            return InquiryEntity.builder().
                    id(arDto.getId()).
                    answer(arDto.getAnswer()).build();

        }
    }

    @Builder
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
