package projectbuildup.mivv.domain.challenge.entity;

import projectbuildup.mivv.domain.member.entity.Member;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.time.LocalDate;
import java.util.List;

public class Challenge extends BaseTimeEntity {

    private int id;

    private String mainTitle;
    private String subTitle;

    private LocalDate startDate;
    private LocalDate endDate;

    private List<String> Content;

    private int remittanceOnceLimit;
    private int remittanceAvailableCount;

    private String imageUrl;



}
