package projectbuildup.mivv.domain.archiving.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import projectbuildup.mivv.domain.archiving.entity.NumericalConditionCardEntity;
import projectbuildup.mivv.domain.archiving.entity.UserCardEntity;

import java.time.LocalDate;

public class ArchivingDto {

    @AllArgsConstructor
    @Data
    public static class createNumericalConditionCardRequestDto {

        @NotBlank
        @Length(min = 1, max = 30)
        @Schema(description = "카드 종류")
        private String kind;

        @NotBlank
        @Length(min = 2, max = 30)
        @Schema(description = "카드 제목")
        private String title;

        @NotBlank
        @Length(min = 2, max = 30)
        @Schema(description = "카드 부제목")
        private String subTitle;

        @NotBlank
        @Length(min = 2, max = 30)
        @Schema(description = "카드 명언")
        private String sentence;

        @NotBlank
        @Length(min = 2, max = 5000)
        @Schema(description = "카드 이미지 URL")
        private String image;

        @Schema(description = "발급조건 금액")
        private Integer charge;

        @Schema(description = "발급조건 횟수")
        private Integer count;

        @Schema(description = "발급조건 일수")
        private Integer term;

        public static NumericalConditionCardEntity toEntity(final ArchivingDto.createNumericalConditionCardRequestDto dto) {

            return NumericalConditionCardEntity.builder()
                    .kind(dto.getKind())
                    .title(dto.getTitle())
                    .subTitle(dto.getSubTitle())
                    .sentence(dto.getSentence())
                    .image(dto.getImage())
                    .charge(dto.getCharge())
                    .count(dto.getCount())
                    .term(dto.getTerm())
                    .build();

        }
    }

    @AllArgsConstructor
    @Data
    public static class updateNumericalConditionCardRequestDto {

        @Length(min = 1, max = 30)
        @Schema(description = "카드 종류")
        private String kind;

        @Length(min = 2, max = 30)
        @Schema(description = "카드 제목")
        private String title;

        @Length(min = 2, max = 30)
        @Schema(description = "카드 부제목")
        private String subTitle;

        @Length(min = 2, max = 30)
        @Schema(description = "카드 명언")
        private String sentence;

        @Length(min = 2, max = 5000)
        @Schema(description = "카드 이미지 URL")
        private String image;

        @Schema(description = "발급조건 금액")
        private Integer charge;

        @Schema(description = "발급조건 횟수")
        private Integer count;

        @Schema(description = "발급조건 일수")
        private Integer term;

        public static NumericalConditionCardEntity toEntity(final ArchivingDto.updateNumericalConditionCardRequestDto dto) {

            return NumericalConditionCardEntity.builder()
                    .kind(dto.getKind())
                    .title(dto.getTitle())
                    .subTitle(dto.getSubTitle())
                    .sentence(dto.getSentence())
                    .image(dto.getImage())
                    .charge(dto.getCharge())
                    .count(dto.getCount())
                    .term(dto.getTerm())
                    .build();

        }
    }

    @AllArgsConstructor
    @Data
    public static class NumericalConditionCardResponseDto {

        @Schema(description = "카드 Id")
        private Long id;

        @Schema(description = "카드 종류")
        private String kind;

        @Schema(description = "카드 제목")
        private String title;

        @Schema(description = "카드 부제목")
        private String subTitle;

        @Length(min = 2, max = 30)
        @Schema(description = "카드 명언")
        private String sentence;

        @Length(min = 2, max = 5000)
        @Schema(description = "카드 이미지 URL")
        private String image;

        @Schema(description = "발급조건 금액")
        private Integer charge;

        @Schema(description = "발급조건 횟수")
        private Integer count;

        @Schema(description = "발급조건 일수")
        private Integer term;

        public NumericalConditionCardResponseDto(final NumericalConditionCardEntity entity) {
            this.id = entity.getId();
            this.kind = entity.getKind();
            this.title = entity.getTitle();
            this.subTitle = entity.getSubTitle();
            this.sentence = entity.getSentence();
            this.image = entity.getImage();
            this.charge = entity.getCharge();
            this.count = entity.getCount();
            this.term = entity.getTerm();
        }

    }

    @AllArgsConstructor
    @Data
    public static class UserCardResponseDto {

        @Schema(description = "UserCard Id")
        private Long id;

        @Schema(description = "카드 정보")
        private NumericalConditionCardResponseDto numericalConditionCardResponseDto;

        @Schema(description = "발급 일자")
        private LocalDate date;

        public UserCardResponseDto(final UserCardEntity entity) {
            this.id = entity.getId();
            this.numericalConditionCardResponseDto = new NumericalConditionCardResponseDto(entity.getNumericalConditionCardEntity());
            this.date = entity.getDate();
        }

    }

}
