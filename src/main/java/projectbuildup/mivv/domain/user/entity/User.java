package projectbuildup.mivv.domain.user.entity;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import projectbuildup.mivv.domain.account.entity.Account;
import projectbuildup.mivv.domain.auth.dto.AuthDto;
import projectbuildup.mivv.global.common.BaseTimeEntity;
import projectbuildup.mivv.global.common.imageStore.Image;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE user SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@Table(name = "user")
public class User extends BaseTimeEntity implements UserDetails {
    private final static String DEFAULT_ROLE = "ROLE_USER";
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "nickname", nullable = false, length = 50)
    String nickname;
    @Column(name = "email", nullable = false, length = 50)
    String email;
    @Column(name = "password", nullable = false)
    String password;
    @Columns(columns = {
            @Column(name = "original_image_name"),
            @Column(name = "image_path"),
            @Column(name = "store_image_name")
    })
    Image profileImage;
    @Column(name = "agreement", nullable = false, length = 1)
    boolean agreement;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "identity_verification_id", foreignKey = @ForeignKey(name = "fk_user_to_iv"))
    IdentityVerification identityVerification;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "fk_user_to_account"))
    Account account;

    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;

    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_to_roles"))
    )
    @Column(name = "roles", length = 30)
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public static User of(AuthDto.SignupRequest requestDto, String encodedPassword, IdentityVerification identityVerification) {
        return User.builder()
                .email(requestDto.getEmail())
                .agreement(requestDto.getAgreement())
                .nickname(requestDto.getNickname())
                .password(encodedPassword)
                .identityVerification(identityVerification)
                .roles(Collections.singletonList(DEFAULT_ROLE))
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return String.valueOf(id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void updateProfile(String nickname, Image image) {
        this.nickname = nickname;
        this.profileImage = image;
    }
}
