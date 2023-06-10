package projectbuildup.mivv.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "identity_verification")
public class IdentityVerification extends BaseTimeEntity {
    private final static String DUMMY_NAME = "심재헌";
    private final static String DUMMY_BIRTHDATE = "19990101";
    private final static String DUMMY_MOBILE = "01000000000";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", length = 50, nullable = false)
    String name;

    @Column(name = "birth_date", length = 20, nullable = false)
    String birthDate;

    @Column(name = "mobile", length = 20, nullable = false)
    String mobile;

    @Column(name = "code", length = 20, nullable = false)
    String code;

    @OneToOne(mappedBy = "identityVerification", fetch = FetchType.LAZY)
    User user;

    public static IdentityVerification generateDummyVerification(){
        String dummyCode = RandomStringUtils.randomAlphabetic(16);
        return IdentityVerification.builder()
                .name(DUMMY_NAME)
                .birthDate(DUMMY_BIRTHDATE)
                .mobile(DUMMY_MOBILE)
                .code(dummyCode)
                .build();
    }
    public static IdentityVerification generateVerification(String name, String birthDate, String mobile){
        String dummyCode = RandomStringUtils.randomAlphabetic(16);
        return IdentityVerification.builder()
                .name(name)
                .birthDate(birthDate)
                .mobile(mobile)
                .code(dummyCode)
                .build();
    }


}
