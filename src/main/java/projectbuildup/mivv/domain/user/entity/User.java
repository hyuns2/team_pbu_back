package projectbuildup.mivv.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import projectbuildup.mivv.domain.account.entity.Account;
import projectbuildup.mivv.domain.auth.dto.AuthDto;
import projectbuildup.mivv.global.common.BaseTimeEntity;

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
public class User extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nickname;
    String profileImage;
    String email;
    String password;
    boolean agreement;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    IdentityVerification identityVerification;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    Account account;

    public static User of (AuthDto.SignupRequest requestDto, String encodedPassword, IdentityVerification identityVerification){
        return User.builder()
                .email(requestDto.getEmail())
                .agreement(requestDto.getAgreement())
                .nickname(requestDto.getNickname())
                .password(encodedPassword)
                .identityVerification(identityVerification)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

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
}
