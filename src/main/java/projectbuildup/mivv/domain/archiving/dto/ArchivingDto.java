package projectbuildup.mivv.domain.archiving.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.archiving.entity.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArchivingDto {

    @AllArgsConstructor
    @Data
    public static class createOrUpdateRemittanceCardRequestDto {

        @NotBlank
        @Length(min = 2, max = 30)
        @Schema(description = "카드 제목")
        private String title;

        @NotBlank
        @Length(min = 2, max = 30)
        @Schema(description = "카드 부제목")
        private String subTitle;

        @NotNull
        @Size(max = 2)
        @Schema(description = "카드 명언")
        private List<@NotBlank @Length(min = 2, max = 30) String> sentences;

        @NotNull
        @Schema(description = "카드 이미지 파일")
        private MultipartFile image;

        @NotNull
        @Schema(description = "발급조건 금액")
        private Integer charge;

        @NotNull
        @Schema(description = "발급조건 횟수")
        private Integer count;

        @NotNull
        @Schema(description = "발급조건 일수")
        private Integer term;

        public static RemittanceConditionCardEntity toEntity(final createOrUpdateRemittanceCardRequestDto dto, String imagePath) throws IOException {

            return RemittanceConditionCardEntity.builder()
                    .type(CardType.REMITTANCE)
                    .title(dto.getTitle())
                    .subTitle(dto.getSubTitle())
                    .sentences(dto.getSentences().stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(", ")))
                    .imagePath(imagePath)
                    .charge(dto.getCharge())
                    .count(dto.getCount())
                    .term(dto.getTerm())
                    .build();

        }
    }

    @AllArgsConstructor
    @Data
    public static class createOrUpdateGeneralCardRequestDto {

        @NotBlank
        @Length(min = 2, max = 30)
        @Schema(description = "카드 제목")
        private String title;

        @NotBlank
        @Length(min = 2, max = 30)
        @Schema(description = "카드 부제목")
        private String subTitle;

        @NotNull
        @Size(max = 2)
        @Schema(description = "카드 명언")
        private List<@NotBlank @Length(min = 2, max = 30) String> sentences;

        @NotNull
        @Schema(description = "카드 이미지 파일")
        private MultipartFile image;

        public static CardEntity toEntity(final createOrUpdateGeneralCardRequestDto dto, String imagePath) throws IOException {

            return CardEntity.builder()
                    .type(CardType.GENERAL)
                    .title(dto.getTitle())
                    .subTitle(dto.getSubTitle())
                    .sentences(dto.getSentences().stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(", ")))
                    .imagePath(imagePath)
                    .build();

        }
    }

    @AllArgsConstructor
    @Data
    public static class AssignGeneralCardsRequestDto {
        @NotNull
        @Schema(description = "카드 고유번호")
        private Long id;

        @NotNull
        @Schema(description = "첨부 엑셀파일")
        private MultipartFile file;
    }

    @AllArgsConstructor
    @Data
    public static class createOrUpdateCouponCardRequestDto {

        @NotBlank
        @Length(min = 2, max = 30)
        @Schema(description = "카드 제목")
        private String title;

        @NotBlank
        @Length(min = 2, max = 30)
        @Schema(description = "카드 부제목")
        private String subTitle;

        @NotNull
        @Size(max = 2)
        @Schema(description = "카드 명언")
        private List<@NotBlank @Length(min = 2, max = 30) String> sentences;

        @NotNull
        @Schema(description = "카드 이미지 파일")
        private MultipartFile image;

        @NotNull
        @Schema(description = "몇번째 쿠폰 발급자에게 카드를 부여할건가")
        private Integer whatNumber;

        @NotNull
        @Schema(description = "몇개월 연속 쿠폰 발급자에게 카드를 부여할건가")
        private Integer howSuccessive;

        public static CouponConditionCardEntity toEntity(final createOrUpdateCouponCardRequestDto dto, String imagePath) throws IOException {

            return CouponConditionCardEntity.builder()
                    .type(CardType.COUPON)
                    .title(dto.getTitle())
                    .subTitle(dto.getSubTitle())
                    .sentences(dto.getSentences().stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(", ")))
                    .imagePath(imagePath)
                    .whatNumber(dto.getWhatNumber())
                    .howSuccessive(dto.getHowSuccessive())
                    .build();

        }
    }

    @AllArgsConstructor
    @Data
    public static class CardResponseDto {

        @Schema(description = "카드 Id")
        private Long id;

        @Schema(description = "카드 종류")
        private String cardType;

        @Schema(description = "카드 제목")
        private String title;

        @Schema(description = "카드 부제목")
        private String subTitle;

        @Schema(description = "카드 명언")
        private List<String> sentences;

        @Schema(description = "카드 이미지 파일경로")
        private String imagePath;

        public CardResponseDto(final CardEntity entity) {
            String[] stringSentences = entity.getSentences().split(", ");
            List<String> listSentences = Arrays.stream(stringSentences).toList();

            this.id = entity.getId();
            this.cardType = entity.getType().name();
            this.title = entity.getTitle();
            this.subTitle = entity.getSubTitle();
            this.sentences = listSentences;
            this.imagePath = entity.getImagePath();
        }

    }

    @AllArgsConstructor
    @Data
    public static class UserCardResponseDto {

        @Schema(description = "UserCard Id")
        private Long id;

        @Schema(description = "카드 정보")
        private CardResponseDto cardDto;

        @Schema(description = "발급 일자")
        private LocalDate date;

        @Schema(description = "신규 여부")
        private boolean isNew;

        public UserCardResponseDto(final UserCardEntity entity) {
            this.id = entity.getId();
            this.cardDto = new CardResponseDto(entity.getCardEntity());
            this.date = entity.getDate();
            this.isNew = entity.isNew();
        }

    }

    @AllArgsConstructor
    @Data
    public static class SimpleUserCardResponseDto {

        @Schema(description = "UserCard Id")
        private Long id;

        @Schema(description = "발급 일자")
        private LocalDate date;

        @Schema(description = "신규 여부")
        private boolean isNew;

        public SimpleUserCardResponseDto(final UserCardEntity entity) {
            this.id = entity.getId();
            this.date = entity.getDate();
            this.isNew = entity.isNew();
        }

    }

    @Data
    public static class CardAndUserCardResponseDto {

        @Schema(description = "카드 정보")
        private CardResponseDto cardDto;

        @Schema(description = "유저카드 정보")
        private SimpleUserCardResponseDto userCardDto;

        public CardAndUserCardResponseDto(final CardEntity cardEntity) {
            this.cardDto = new CardResponseDto(cardEntity);
            this.userCardDto = null;
        }

        public CardAndUserCardResponseDto(final CardEntity cardEntity, final UserCardEntity userCardEntity) {
            this.cardDto = new CardResponseDto(cardEntity);
            this.userCardDto = new SimpleUserCardResponseDto(userCardEntity);
        }

    }

}
