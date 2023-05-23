package projectbuildup.mivv.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import projectbuildup.mivv.global.common.BaseTimeEntity;

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
    @Column(name = "name")
    String name;
    @Column(name = "birth_date")
    String birthDate;
    @Column(name = "mobile")
    String mobile;
    @Column(name = "code")
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
}
