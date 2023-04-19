package projectbuildup.mivv.domain.challenge.service;

import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.remittance.entity.Remittance;

@Component
public class RankScoreCalculator {
    private final static double AMOUNT_FACTOR = 1000;
    private final static double COUNT_FACTOR = 0.001;

    /**
     * 랭킹 점수를 계산합니다.
     * (금액) * (AMOUNT_FACTOR) + (총 절약 횟수) * (COUNT_FACTOR)로 산출합니다.
     *
     * @param remittance    송금액
     * @param participation 참여 정보
     * @return 점수
     */
    public double calculate(Remittance remittance, Participation participation) {
        int count = participation.getRemittanceList().size();
        long amount = remittance.getAmount();
        return amount / AMOUNT_FACTOR + count * COUNT_FACTOR;
    }
}
