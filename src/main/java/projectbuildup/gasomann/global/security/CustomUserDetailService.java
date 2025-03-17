package projectbuildup.gasomann.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import projectbuildup.gasomann.domain.user.repository.UserRepository;
import projectbuildup.gasomann.global.error.exception.CUserNotFoundException;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return memberRepository.findById(Long.parseLong(memberId)).orElseThrow(CUserNotFoundException::new);
    }
}
