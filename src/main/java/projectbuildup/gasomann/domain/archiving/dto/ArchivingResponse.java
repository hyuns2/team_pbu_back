package projectbuildup.gasomann.domain.archiving.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import projectbuildup.gasomann.domain.archiving.entity.*;

import java.time.LocalDate;
import java.util.List;

public class ArchivingResponse {
    @Getter
    @SuperBuilder
    public static abstract class CardDto {
        @Schema(description = "카드 Id")
        private Long id;

        @Schema(description = "카드 종류")
        private CardType cardType;

        @Schema(description = "카드 제목")
        private String title;

        @Schema(description = "카드 부제목")
        private String subTitle;

        @Schema(description = "카드 명언")
        private List<String> sentences;

        @Schema(description = "카드 이미지 파일경로")
        private String imagePath;
    }

    @Getter
    @SuperBuilder
    public static class SavingCardDto extends CardDto {
        @Schema(description = "발급조건 금액")
        private Integer charge;

        @Schema(description = "발급조건 횟수")
        private Integer count;

        @Schema(description = "발급조건 일수")
        private Integer term;

        public static SavingCardDto from(SavingCard card) {
            return SavingCardDto.builder()
                    .id(card.getId())
                    .cardType(card.getCardType())
                    .title(card.getTitle())
                    .subTitle(card.getSubTitle())
                    .sentences(card.getSentences())
                    .imagePath(card.getImagePath())
                    .charge(card.getCharge())
                    .count(card.getCount())
                    .term(card.getTerm()).build();
        }
    }

    @Getter
    @SuperBuilder
    public static class CouponCardDto extends CardDto {
        @Schema(description = "몇번째 쿠폰 발급자에게 카드를 부여할건가")
        private Integer couponIssueOrder;

        @Schema(description = "몇개월 연속 쿠폰 발급자에게 카드를 부여할건가")
        private Integer continuousIssueMonths;

        public static CouponCardDto from(CouponCard card) {
            return CouponCardDto.builder()
                    .id(card.getId())
                    .cardType(card.getCardType())
                    .title(card.getTitle())
                    .subTitle(card.getSubTitle())
                    .sentences(card.getSentences())
                    .imagePath(card.getImagePath())
                    .couponIssueOrder(card.getCouponIssueOrder())
                    .continuousIssueMonths(card.getContinuousIssueMonths()).build();
        }
    }

    @Getter
    @SuperBuilder
    public static class GeneralCardDto extends CardDto {
        public static GeneralCardDto from(GeneralCard card) {
            return GeneralCardDto.builder()
                    .id(card.getId())
                    .cardType(card.getCardType())
                    .title(card.getTitle())
                    .subTitle(card.getSubTitle())
                    .sentences(card.getSentences())
                    .imagePath(card.getImagePath()).build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class UserCardWithCardSummaryDto {
        @Schema(description = "UserCard Id")
        private Long id;

        @Schema(description = "카드 정보")
        private CardSummaryDto cardSummaryDto;

        @Schema(description = "발급 일자")
        private LocalDate date;

        @Schema(description = "신규 여부")
        private Boolean isNew;

        public static UserCardWithCardSummaryDto from(UserCard userCard) {
            return UserCardWithCardSummaryDto.builder()
                    .id(userCard.getId())
                    .cardSummaryDto(CardSummaryDto.from(userCard.getCard()))
                    .date(userCard.getDate())
                    .isNew(userCard.getIsNew()).build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class CardSummaryDto {
        @Schema(description = "카드 Id")
        private Long id;

        @Schema(description = "카드 종류")
        private CardType cardType;

        @Schema(description = "카드 제목")
        private String title;

        public static CardSummaryDto from(Card card) {
            return CardSummaryDto.builder()
                    .id(card.getId())
                    .cardType(card.getCardType())
                    .title(card.getTitle()).build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class CardSummaryAndUserCardDto {
        @Schema(description = "카드 요약정보")
        private CardSummaryDto cardSummaryDto;

        @Schema(description = "UserCard 정보")
        private UserCardDto userCardDto;

        public static CardSummaryAndUserCardDto from(Card card, UserCard userCard) {
            return CardSummaryAndUserCardDto.builder()
                    .cardSummaryDto(CardSummaryDto.from(card))
                    .userCardDto(userCard != null ? UserCardDto.from(userCard) : null).build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class UserCardDto {
        @Schema(description = "UserCard Id")
        private Long id;

        @Schema(description = "발급 일자")
        private LocalDate date;

        @Schema(description = "신규 여부")
        private Boolean isNew;

        public static UserCardDto from(UserCard userCard) {
            return UserCardDto.builder()
                    .id(userCard.getId())
                    .date(userCard.getDate())
                    .isNew(userCard.getIsNew()).build();
        }
    }
}
