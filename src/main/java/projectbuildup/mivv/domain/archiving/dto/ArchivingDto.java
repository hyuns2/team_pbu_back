package projectbuildup.mivv.domain.archiving.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.archiving.entity.CardEntity;
import projectbuildup.mivv.domain.archiving.entity.CouponConditionCardEntity;
import projectbuildup.mivv.domain.archiving.entity.NumericalConditionCardEntity;
import projectbuildup.mivv.domain.archiving.entity.UserCardEntity;

import java.io.IOException;
import java.time.LocalDate;

public class ArchivingDto {

    @AllArgsConstructor
    @Data
    public static class createNumericalCardRequestDto {

        @NotBlank
        @Length(min = 2, max = 30)
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

        @NotNull
        @Schema(description = "카드 이미지 파일")
        private MultipartFile image;

        @Schema(description = "발급조건 금액")
        private Integer charge;

        @Schema(description = "발급조건 횟수")
        private Integer count;

        @Schema(description = "발급조건 일수")
        private Integer term;

        public static NumericalConditionCardEntity toEntity(final createNumericalCardRequestDto dto, String imagePath) throws IOException {

            return NumericalConditionCardEntity.builder()
                    .kind(dto.getKind())
                    .title(dto.getTitle())
                    .subTitle(dto.getSubTitle())
                    .sentence(dto.getSentence())
                    .imagePath(imagePath)
                    .charge(dto.getCharge())
                    .count(dto.getCount())
                    .term(dto.getTerm())
                    .build();

        }
    }

    @AllArgsConstructor
    @Data
    public static class updateNumericalCardRequestDto {

        @Length(max = 30)
        @Schema(description = "카드 종류")
        private String kind;

        @Length(max = 30)
        @Schema(description = "카드 제목")
        private String title;

        @Length(max = 30)
        @Schema(description = "카드 부제목")
        private String subTitle;

        @Length(max = 30)
        @Schema(description = "카드 명언")
        private String sentence;

        @Schema(description = "카드 이미지 파일")
        private MultipartFile image;

        @Schema(description = "발급조건 금액")
        private Integer charge;

        @Schema(description = "발급조건 횟수")
        private Integer count;

        @Schema(description = "발급조건 일수")
        private Integer term;

    }

    @AllArgsConstructor
    @Data
    public static class createGeneralCardRequestDto {

        @NotBlank
        @Length(min = 2, max = 30)
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

        @NotNull
        @Schema(description = "카드 이미지 파일")
        private MultipartFile image;

        public static CardEntity toEntity(final ArchivingDto.createGeneralCardRequestDto dto, String imagePath) throws IOException {

            return CardEntity.builder()
                    .kind(dto.getKind())
                    .title(dto.getTitle())
                    .subTitle(dto.getSubTitle())
                    .sentence(dto.getSentence())
                    .imagePath(imagePath)
                    .build();

        }
    }

    @AllArgsConstructor
    @Data
    public static class updateGeneralCardRequestDto {

        @Length(max = 30)
        @Schema(description = "카드 종류")
        private String kind;

        @Length(max = 30)
        @Schema(description = "카드 제목")
        private String title;

        @Length(max = 30)
        @Schema(description = "카드 부제목")
        private String subTitle;

        @Length(max = 30)
        @Schema(description = "카드 명언")
        private String sentence;

        @Schema(description = "카드 이미지 파일")
        private MultipartFile image;

    }

    @AllArgsConstructor
    @Data
    public static class AssignGeneralCardsRequestDto {
        @NotNull
        @Schema(description = "카드 고유번호")
        private Long id;

        @NotNull
        @Schema(description = "첨부 파일")
        private MultipartFile file;
    }

    @AllArgsConstructor
    @Data
    public static class createCouponCardRequestDto {

        @NotBlank
        @Length(min = 2, max = 30)
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

        @NotNull
        @Schema(description = "카드 이미지 파일")
        private MultipartFile image;

        @NotNull
        @Schema(description = "몇번째 쿠폰 발급자에게 카드를 부여할건가")
        private Integer whatNumber;

        @NotNull
        @Schema(description = "몇개월 연속 쿠폰 발급자에게 카드를 부여할건가")
        private Integer howSuccessive;

        public static CouponConditionCardEntity toEntity(final ArchivingDto.createCouponCardRequestDto dto, String imagePath) throws IOException {

            return CouponConditionCardEntity.builder()
                    .kind(dto.getKind())
                    .title(dto.getTitle())
                    .subTitle(dto.getSubTitle())
                    .sentence(dto.getSentence())
                    .imagePath(imagePath)
                    .whatNumber(dto.getWhatNumber())
                    .howSuccessive(dto.getHowSuccessive())
                    .build();

        }
    }

    @AllArgsConstructor
    @Data
    public static class updateCouponCardRequestDto {

        @Length(max = 30)
        @Schema(description = "카드 종류")
        private String kind;

        @Length(max = 30)
        @Schema(description = "카드 제목")
        private String title;

        @Length(max = 30)
        @Schema(description = "카드 부제목")
        private String subTitle;

        @Length(max = 30)
        @Schema(description = "카드 명언")
        private String sentence;

        @Schema(description = "카드 이미지 파일")
        private MultipartFile image;

        @Schema(description = "몇번째 쿠폰 발급자에게 카드를 부여할건가")
        private Integer whatNumber;

        @Schema(description = "몇개월 연속 쿠폰 발급자에게 카드를 부여할건가")
        private Integer howSuccessive;

    }

    @AllArgsConstructor
    @Data
    public static class CardResponseDto {

        @Schema(description = "카드 Id")
        private Long id;

        @Schema(description = "카드 종류")
        private String kind;

        @Schema(description = "카드 제목")
        private String title;

        @Schema(description = "카드 부제목")
        private String subTitle;

        @Schema(description = "카드 명언")
        private String sentence;

        @Schema(description = "카드 이미지 파일경로")
        private String imagePath;

        public CardResponseDto(final CardEntity entity) {
            this.id = entity.getId();
            this.kind = entity.getKind();
            this.title = entity.getTitle();
            this.subTitle = entity.getSubTitle();
            this.sentence = entity.getSentence();
            this.imagePath = entity.getImagePath();
        }

    }

    @AllArgsConstructor
    @Data
    public static class UserCardResponseDto {

        @Schema(description = "UserCard Id")
        private Long id;

        @Schema(description = "카드 정보")
        private CardResponseDto cardResponseDto;

        @Schema(description = "발급 일자")
        private LocalDate date;

        @Schema(description = "신규 여부")
        private boolean isNew;

        public UserCardResponseDto(final UserCardEntity entity) {
            this.id = entity.getId();
            this.cardResponseDto = new CardResponseDto(entity.getCardEntity());
            this.date = entity.getDate();
            this.isNew = entity.isNew();
        }

    }

}
