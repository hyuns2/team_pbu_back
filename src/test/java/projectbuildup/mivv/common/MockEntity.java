package projectbuildup.mivv.common;

import projectbuildup.mivv.domain.account.entity.Account;
import projectbuildup.mivv.domain.account.entity.BankType;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;
import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDate;

public class MockEntity {

    public static final User MOCK_USER;
    public static final Account MOCK_ACCOUNT;
    public static final IdentityVerification MOCK_IDENTITY_VERIFICATION;
    public static final Challenge MOCK_CHALLENGE;
    public static final Participation MOCK_PARTICIPATION;

    public static final Remittance MOCK_REMITTANCE_1;
    public static final Remittance MOCK_REMITTANCE_2;
    public static final Remittance MOCK_REMITTANCE_3;
    public static final Remittance MOCK_REMITTANCE_4;

    static{
        MOCK_ACCOUNT = Account.builder()
                .accountNumbers("0123456789")
                .bankType(BankType.CITY)
                .build();
        MOCK_IDENTITY_VERIFICATION = IdentityVerification.builder()
                .name("김철수")
                .birthDate("19990101")
                .mobile("01012345678")
                .code("abcdefg")
                .build();
        MOCK_USER = User.builder()
                .nickname("테스트유저")
                .profileImage("image")
                .email("test@naver.com")
                .password("test")
                .agreement(true)
                .account(MOCK_ACCOUNT)
                .identityVerification(MOCK_IDENTITY_VERIFICATION)
                .build();
        MOCK_CHALLENGE = Challenge.builder()
                .mainTitle("챌린지 제목")
                .subTitle("챌린지 부제목")
                .remittanceOnceLimit(1000L)
                .remittanceAvailableCount(5)
                .imageUrl("image")
                .startDate(LocalDate.of(2023, 1, 1))
                .endDate(LocalDate.of(2024, 1, 1))
                .build();
        MOCK_PARTICIPATION = new Participation(MOCK_USER, MOCK_CHALLENGE);
    }
    static{
        MOCK_REMITTANCE_1 = new Remittance(1000L, MOCK_PARTICIPATION);
        MOCK_REMITTANCE_2 = new Remittance(2000L, MOCK_PARTICIPATION);
        MOCK_REMITTANCE_3 = new Remittance(-500L, MOCK_PARTICIPATION);
        MOCK_REMITTANCE_4 = new Remittance(3000L, MOCK_PARTICIPATION);
    }
}
