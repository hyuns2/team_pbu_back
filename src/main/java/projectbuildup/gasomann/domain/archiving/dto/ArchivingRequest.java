package projectbuildup.gasomann.domain.archiving.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.gasomann.domain.archiving.entity.CardType;
import projectbuildup.gasomann.domain.archiving.entity.CouponCard;
import projectbuildup.gasomann.domain.archiving.entity.GeneralCard;
import projectbuildup.gasomann.domain.archiving.entity.SavingCard;

import java.util.List;

@RequiredArgsConstructor
public class ArchivingRequest {
    @Getter
    @NoArgsConstructor
    @SuperBuilder
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "cardType",
        visible = true
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(name = "SAVING", value = SavingCardCreateDto.class),
            @JsonSubTypes.Type(name = "COUPON", value = CouponCardCreateDto.class),
            @JsonSubTypes.Type(name = "GENERAL", value = GeneralCardCreateDto.class)
    })
    public static abstract class CardCreateDto {
        @NotNull
        @Schema(description = "카드 타입")
        private CardType cardType;

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
    }

    @Getter
    @NoArgsConstructor
    @SuperBuilder
    public static class SavingCardCreateDto extends CardCreateDto {
        @NotNull
        @Schema(description = "발급조건 금액")
        private Integer charge;

        @NotNull
        @Schema(description = "발급조건 횟수")
        private Integer count;

        @NotNull
        @Schema(description = "발급조건 일수")
        private Integer term;

        public static SavingCardCreateDto of(String title, String subTitle, List<String> sentences,
                                             Integer charge, Integer count, Integer term) {
            return SavingCardCreateDto.builder()
                    .cardType(CardType.SAVING)
                    .title(title)
                    .subTitle(subTitle)
                    .sentences(sentences)
                    .charge(charge)
                    .count(count)
                    .term(term).build();
        }

        public static SavingCard toEntity(SavingCardCreateDto dto, String imagePath) {
            return SavingCard.of(dto.getTitle(), dto.getSubTitle(), dto.getSentences(), imagePath,
                    dto.getCharge(), dto.getCount(), dto.getTerm());
        }
    }

    @Getter
    @NoArgsConstructor
    @SuperBuilder
    public static class CouponCardCreateDto extends CardCreateDto {
        @NotNull
        @Schema(description = "몇번째 쿠폰 발급자에게 카드를 부여할건가")
        private Integer couponIssueOrder;

        @NotNull
        @Schema(description = "몇개월 연속 쿠폰 발급자에게 카드를 부여할건가")
        private Integer continuousIssueMonths;

        public static CouponCardCreateDto of(String title, String subTitle, List<String> sentences,
                                             Integer couponIssueOrder, Integer continuousIssueMonths) {
            return CouponCardCreateDto.builder()
                    .cardType(CardType.COUPON)
                    .title(title)
                    .subTitle(subTitle)
                    .sentences(sentences)
                    .couponIssueOrder(couponIssueOrder)
                    .continuousIssueMonths(continuousIssueMonths).build();
        }

        public static CouponCard toEntity(CouponCardCreateDto dto, String imagePath) {
            return CouponCard.of(dto.getTitle(), dto.getSubTitle(), dto.getSentences(), imagePath,
                    dto.getCouponIssueOrder(), dto.getContinuousIssueMonths());
        }
    }

    @Getter
    @NoArgsConstructor
    @SuperBuilder
    public static class GeneralCardCreateDto extends CardCreateDto {
        public static GeneralCardCreateDto of(String title, String subTitle, List<String> sentences) {
            return GeneralCardCreateDto.builder()
                    .cardType(CardType.GENERAL)
                    .title(title)
                    .subTitle(subTitle)
                    .sentences(sentences).build();
        }

        public static GeneralCard toEntity(GeneralCardCreateDto dto, String imagePath) {
            return GeneralCard.of(dto.getTitle(), dto.getSubTitle(), dto.getSentences(), imagePath);
        }
    }

    @Getter
    @NoArgsConstructor
    @SuperBuilder
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXISTING_PROPERTY,
            property = "cardType",
            visible = true
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(name = "SAVING", value = SavingCardUpdateDto.class),
            @JsonSubTypes.Type(name = "COUPON", value = CouponCardUpdateDto.class),
            @JsonSubTypes.Type(name = "GENERAL", value = GeneralCardUpdateDto.class)
    })
    public static abstract class CardUpdateDto {
        @NotNull
        @Schema(description = "카드 타입")
        private CardType cardType;

        @Length(min = 2, max = 30)
        @Schema(description = "카드 제목")
        private String title;

        @Length(min = 2, max = 30)
        @Schema(description = "카드 부제목")
        private String subTitle;

        @Size(max = 2)
        @Schema(description = "카드 명언")
        private List<@NotBlank @Length(min = 2, max = 30) String> sentences;
    }

    @Getter
    @NoArgsConstructor
    @SuperBuilder
    public static class SavingCardUpdateDto extends CardUpdateDto {
        @Schema(description = "발급조건 금액")
        private Integer charge;

        @Schema(description = "발급조건 횟수")
        private Integer count;

        @Schema(description = "발급조건 일수")
        private Integer term;

        public static SavingCardUpdateDto of(String title, String subTitle, List<String> sentences,
                                             Integer charge, Integer count, Integer term) {
            return SavingCardUpdateDto.builder()
                    .cardType(CardType.SAVING)
                    .title(title)
                    .subTitle(subTitle)
                    .sentences(sentences)
                    .charge(charge)
                    .count(count)
                    .term(term).build();
        }
    }

    @Getter
    @NoArgsConstructor
    @SuperBuilder
    public static class CouponCardUpdateDto extends CardUpdateDto {
        @Schema(description = "몇번째 쿠폰 발급자에게 카드를 부여할건가")
        private Integer couponIssueOrder;

        @Schema(description = "몇개월 연속 쿠폰 발급자에게 카드를 부여할건가")
        private Integer continuousIssueMonths;

        public static CouponCardUpdateDto of(String title, String subTitle, List<String> sentences,
                                             Integer couponIssueOrder, Integer continuousIssueMonths) {
            return CouponCardUpdateDto.builder()
                    .cardType(CardType.COUPON)
                    .title(title)
                    .subTitle(subTitle)
                    .sentences(sentences)
                    .couponIssueOrder(couponIssueOrder)
                    .continuousIssueMonths(continuousIssueMonths).build();
        }
    }

    @Getter
    @NoArgsConstructor
    @SuperBuilder
    public static class GeneralCardUpdateDto extends CardUpdateDto {
        public static GeneralCardUpdateDto of(String title, String subTitle, List<String> sentences) {
            return GeneralCardUpdateDto.builder()
                    .cardType(CardType.GENERAL)
                    .title(title)
                    .subTitle(subTitle)
                    .sentences(sentences).build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class GeneralCardAssignDto {
        @NotNull
        @Schema(description = "카드 고유번호")
        private Long id;

        @NotNull
        @Schema(description = "첨부 엑셀파일")
        private MultipartFile file;

        public static GeneralCardAssignDto of(Long id, MultipartFile file) {
            return GeneralCardAssignDto.builder()
                    .id(id)
                    .file(file).build();
        }
    }
}
