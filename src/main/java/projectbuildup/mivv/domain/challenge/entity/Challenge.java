package projectbuildup.mivv.domain.challenge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import projectbuildup.mivv.domain.member.entity.Member;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.time.LocalDate;
import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Challenge extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Setter
    @NonNull
    private String mainTitle;
    @Setter
    @NonNull
    private String subTitle;
    @NonNull
    private LocalDate startDate;
    @NonNull
    private LocalDate endDate;
    @NonNull
    @Setter
    private List<String> Content;
    @NonNull
    private int remittanceOnceLimit;
    @NonNull
    private int remittanceAvailableCount;
    @NonNull
    private String imageUrl;



}
