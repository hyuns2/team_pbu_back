package projectbuildup.mivv.domain.archiving.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import projectbuildup.mivv.domain.archiving.entity.NumericalConditionCardEntity;

@NoArgsConstructor
public class ArchivingDto {

    @AllArgsConstructor
    @Data
    public static class NumericalConditionCardRequestDto {

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

        public static NumericalConditionCardEntity toEntity(final ArchivingDto.NumericalConditionCardRequestDto dto) {

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

}
