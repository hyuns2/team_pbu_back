package projectbuildup.mivv.domain.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.account.dto.AccountRegisterDto;
import projectbuildup.mivv.domain.account.entity.Account;
import projectbuildup.mivv.domain.account.repository.AccountRepository;
import projectbuildup.mivv.domain.account.service.accountsystem.AccountSystem;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountRegisterService {
    private final AccountSystem accountSystem;
    private final UserRepository userRepository;

    /**
     * 계좌를 등록합니다.
     *
     * @param requestDto 계좌 정보, 아이디/비밀번호 혹은 인증서
     * @param userId     회원 아이디넘버
     */
    public void registerAccount(AccountRegisterDto requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        Account account = accountSystem.createAccount(requestDto, user);
        user.setAccount(account);
        userRepository.save(user);
    }
}
