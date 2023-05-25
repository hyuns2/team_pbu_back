package projectbuildup.mivv.integrationtest.setting;

import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.imageStore.Image;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MockEntityFactory {

    public static IdentityVerification mockIdentityVerification() {
        return IdentityVerification.builder()
                .name("test_name")
                .birthDate("test_birth")
                .mobile("test_mobile")
                .code("test_code")
                .build();
    }

    public static User mockUser(IdentityVerification identityVerification, String nickname) {
        return User.builder()
                .email("test@email.com")
                .agreement(true)
                .nickname(nickname)
                .password("{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO") //123456
                .identityVerification(identityVerification)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }

    public static Image mockImage() {
        return new Image("original_name", UUID.randomUUID().toString(), "./../files/mock-image1.jpg");
    }

    public static Challenge mockChallenge(Image image, String title) {
        return Challenge.builder()
                .mainTitle(title)
                .subTitle("test_subtitle")
                .description(List.of("test_description1", "test_description2"))
                .maxSavingAmount(10000)
                .minSavingAmount(1000)
                .limitedNumberOfTimes(5)
                .image(image)
                .startDate(LocalDate.of(2023, 5, 1))
                .endDate(LocalDate.of(2024, 5, 1))
                .build();
    }
}
