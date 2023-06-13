package projectbuildup.mivv.domain.challenge.service;

import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.remittance.entity.Remittance;

@Component
public class RankScoreCalculator {
    private final static double AMOUNT_FACTOR = 1;
    private final static double COUNT_FACTOR = 1;

    /**
     * 랭킹 점수를 계산합니다.
     * (금액) / (AMOUNT_FACTOR) + (COUNT_FACTOR)로 산출합니다.
     *
     * @param remittance    송금액
     * @return 점수
     */
    public double calculate(Remittance remittance) {
        long amount = remittance.getAmount();
        return amount / AMOUNT_FACTOR + COUNT_FACTOR;
    }
}
