package projectbuildup.mivv.domain.archiving.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import projectbuildup.mivv.domain.archiving.entity.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ArchivingResponse {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @SuperBuilder
    public static class CardDto {
        @Schema(description = "카드 Id")
        private Long id;

        @Schema(description = "카드 종류")
        private String type;

        @Schema(description = "카드 제목")
        private String title;

        @Schema(description = "카드 부제목")
        private String subTitle;

        @Schema(description = "카드 명언")
        private List<String> sentences;

        @Schema(description = "카드 이미지 파일경로")
        private String imagePath;

        public static CardDto of(Card card) {
            return CardDto.builder()
                    .id(card.getId())
                    .type(card.getType().toString())
                    .title(card.getTitle())
                    .subTitle(card.getSubTitle())
                    .sentences(Arrays.stream(card.getSentences().split(", ")).toList())
                    .imagePath(card.getImagePath()).build();
        }
    }

    @Getter
    @AllArgsConstructor
    @SuperBuilder
    public static class SavingCardDto extends CardDto {
        @Schema(description = "발급조건 금액")
        private Integer charge;

        @Schema(description = "발급조건 횟수")
        private Integer count;

        @Schema(description = "발급조건 일수")
        private Integer term;

        public static SavingCardDto of(SavingCard card) {
            return SavingCardDto.builder()
                    .id(card.getId())
                    .type(card.getType().toString())
                    .title(card.getTitle())
                    .subTitle(card.getSubTitle())
                    .sentences(Arrays.stream(card.getSentences().split(", ")).toList())
                    .imagePath(card.getImagePath())
                    .charge(card.getCharge())
                    .count(card.getCount())
                    .term(card.getTerm()).build();
        }
    }

    @Getter
    @AllArgsConstructor
    @SuperBuilder
    public static class CouponCardDto extends CardDto {
        @Schema(description = "몇번째 쿠폰 발급자에게 카드를 부여할건가")
        private Integer whatNumber;

        @Schema(description = "몇개월 연속 쿠폰 발급자에게 카드를 부여할건가")
        private Integer howSuccessive;

        public static CouponCardDto of(CouponCard card) {
            return CouponCardDto.builder()
                    .id(card.getId())
                    .type(card.getType().toString())
                    .title(card.getTitle())
                    .subTitle(card.getSubTitle())
                    .sentences(Arrays.stream(card.getSentences().split(", ")).toList())
                    .imagePath(card.getImagePath())
                    .whatNumber(card.getWhatNumber())
                    .howSuccessive(card.getHowSuccessive()).build();
        }
    }

    @Getter
    @AllArgsConstructor
    @SuperBuilder
    public static class GeneralCardDto extends CardDto {
        public static GeneralCardDto of(GeneralCard card) {
            return GeneralCardDto.builder()
                    .id(card.getId())
                    .type(card.getType().toString())
                    .title(card.getTitle())
                    .subTitle(card.getSubTitle())
                    .sentences(Arrays.stream(card.getSentences().split(", ")).toList())
                    .imagePath(card.getImagePath()).build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class UserCardDto {
        @Schema(description = "UserCard Id")
        private Long id;

        @Schema(description = "카드 정보")
        private CardSummaryDto cardDto;

        @Schema(description = "발급 일자")
        private LocalDate date;

        @Schema(description = "신규 여부")
        private boolean isNew;

        public static UserCardDto of(UserCard userCard) {
            return UserCardDto.builder()
                    .id(userCard.getId())
                    .cardDto(CardSummaryDto.of(userCard.getCard()))
                    .date(userCard.getDate())
                    .isNew(userCard.isNew()).build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class CardSummaryDto {
        @Schema(description = "카드 Id")
        private Long id;

        @Schema(description = "카드 종류")
        private String type;

        @Schema(description = "카드 제목")
        private String title;

        public static CardSummaryDto of(Card card) {
            return CardSummaryDto.builder()
                    .id(card.getId())
                    .type(card.getType().toString())
                    .title(card.getTitle()).build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class AllCardSummaryAndUserCardDto {
        @Schema(description = "카드 요약정보")
        private CardSummaryDto cardDto;

        @Schema(description = "UserCard 정보")
        private UserCardDto userCardDto;

        public static AllCardSummaryAndUserCardDto of(Card card, UserCard userCard) {
            return AllCardSummaryAndUserCardDto.builder()
                    .cardDto(CardSummaryDto.of(card))
                    .userCardDto(UserCardDto.of(userCard)).build();
        }
    }
}
