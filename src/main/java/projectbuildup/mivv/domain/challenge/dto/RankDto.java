package projectbuildup.mivv.domain.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RankDto {
    @Getter
    @AllArgsConstructor
    public static class Unit {
        long rank;
        long userId;
    }
    @Getter
    @AllArgsConstructor
    public static class Group {
        Unit theFirst;
        List<Unit> nearbyRanking;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class UnitResponse {
        long rank;
        long userId;
        String nickname;
        String profileImage;
        long amount;
    }

    @Getter
    @AllArgsConstructor
    public static class GroupResponse {
        UnitResponse first;
        List<UnitResponse> ranking;
    }

    @Getter
    @AllArgsConstructor
    public static class ShortResponse{
        Long challengeId;
        String title;
        Long rank;
    }
}
