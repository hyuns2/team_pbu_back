package projectbuildup.mivv.common;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import projectbuildup.mivv.domain.account.entity.Account;
import projectbuildup.mivv.domain.account.entity.BankType;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;
import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockEntity {

    public static final User MOCK_USER;
    public static final Account MOCK_ACCOUNT;
    public static final IdentityVerification MOCK_IDENTITY_VERIFICATION;
    public static final Challenge MOCK_CHALLENGE;

    static{
        MOCK_ACCOUNT = Account.builder()
                .id(1L)
                .accountNumbers("0123456789")
                .bankType(BankType.CITY)
                .build();
        MOCK_IDENTITY_VERIFICATION = IdentityVerification.builder()
                .id(1L)
                .name("김철수")
                .birthDate("19990101")
                .mobile("01012345678")
                .code("abcdefg")
                .build();
        MOCK_USER = User.builder()
                .id(1L)
                .nickname("테스트유저")
                .profileImage("image")
                .email("test@naver.com")
                .password("test")
                .agreement(true)
                .account(MOCK_ACCOUNT)
                .identityVerification(MOCK_IDENTITY_VERIFICATION)
                .build();
        MOCK_CHALLENGE = Challenge.builder()
                .id(1L)
                .mainTitle("챌린지 제목")
                .subTitle("챌린지 부제목")
                .remittanceOnceLimit(1000L)
                .remittanceAvailableCount(10)
                .imageUrl("image")
                .startDate(LocalDate.of(2023, 1, 1))
                .endDate(LocalDate.of(2024, 1, 1))
                .build();
    }
}
