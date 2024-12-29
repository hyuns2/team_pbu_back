package projectbuildup.mivv.domain.archiving.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.archiving.entity.CouponCard;
import projectbuildup.mivv.domain.archiving.entity.GeneralCard;
import projectbuildup.mivv.domain.archiving.entity.SavingCard;

import java.util.List;

public class ArchivingRequest {
    @Getter
    @AllArgsConstructor
    public static class CardDto {
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
    }

    @Getter
    @AllArgsConstructor
    public static class SavingCardDto {
        private CardDto cardDto;

        @NotNull
        @Schema(description = "발급조건 금액")
        private Integer charge;

        @NotNull
        @Schema(description = "발급조건 횟수")
        private Integer count;

        @NotNull
        @Schema(description = "발급조건 일수")
        private Integer term;

        public static SavingCard toEntity(SavingCardDto dto, String imagePath) {
            return SavingCard.of(dto.getCardDto().getTitle(),
                    dto.getCardDto().getSubTitle(),
                    dto.getCardDto().getSentences(),
                    imagePath, dto.getCharge(), dto.getCount(), dto.getTerm());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class CouponCardDto {
        private CardDto cardDto;

        @NotNull
        @Schema(description = "몇번째 쿠폰 발급자에게 카드를 부여할건가")
        private Integer whatNumber;

        @NotNull
        @Schema(description = "몇개월 연속 쿠폰 발급자에게 카드를 부여할건가")
        private Integer howSuccessive;

        public static CouponCard toEntity(CouponCardDto dto, String imagePath) {
            return CouponCard.of(dto.getCardDto().getTitle(),
                    dto.getCardDto().getSubTitle(),
                    dto.getCardDto().getSentences(),
                    imagePath, dto.getWhatNumber(), dto.getHowSuccessive());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class GeneralCardDto {
        private CardDto cardDto;
        public static GeneralCard toEntity(GeneralCardDto dto, String imagePath) {
            return GeneralCard.of(dto.getCardDto().getTitle(),
                    dto.getCardDto().getSubTitle(),
                    dto.getCardDto().getSentences(),
                    imagePath);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class AssignGeneralCardDto {
        @NotNull
        @Schema(description = "카드 고유번호")
        private Long id;

        @NotNull
        @Schema(description = "첨부 엑셀파일")
        private MultipartFile file;
    }
}
