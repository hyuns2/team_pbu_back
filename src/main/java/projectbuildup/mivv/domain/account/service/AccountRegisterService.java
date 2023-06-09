package projectbuildup.mivv.domain.account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.account.dto.AccountCertifyTransferDto;
import projectbuildup.mivv.domain.account.dto.AccountRegisterDto;
import projectbuildup.mivv.domain.account.entity.Account;
import projectbuildup.mivv.domain.account.service.accountsystem.AccountSystem;
import projectbuildup.mivv.domain.auth.repository.IdentityVerificationRepository;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CNotOwnAccountException;
import projectbuildup.mivv.global.error.exception.CResourceNotFoundException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountRegisterService {
    private final AccountSystem accountSystem;
    private final UserRepository userRepository;
    private final IdentityVerificationRepository identityVerificationRepository;
    private final AccountRepository accountRepository;

    /**
     * 계좌를 등록합니다.
     *
     * @param requestDto 계좌 정보, 아이디/비밀번호, 본인인증 코드
     */
    @Transactional
    public void registerAccount(AccountRegisterDto requestDto) {
        IdentityVerification identityVerification = identityVerificationRepository.findByCode(requestDto.getVerificationCode()).orElseThrow(CResourceNotFoundException::new);
        User user = userRepository.findByIdentityVerification(identityVerification).orElseThrow(CUserNotFoundException::new);
        Account account = accountSystem.createAccount(requestDto, user);
        accountRepository.save(account);
    }

    /**
     * 입력한 계좌의 예금주가 본인 인증한 실명과 일치하는지 확인합니다.
     *
     * @param requestDto 기관코드, 계좌번호, 본인 인증 코드
     * @return true/false
     */
    public boolean checkAccountOwner(AccountCertifyTransferDto requestDto) {
        String name = accountSystem.getAccountOwner(requestDto.getOrganizationCode(), requestDto.getAccountNumbers(), requestDto.getVerificationCode());
        IdentityVerification identityVerification = identityVerificationRepository.findByCode(requestDto.getVerificationCode()).orElseThrow(CResourceNotFoundException::new);
        return identityVerification.getName().equals(name);
    }

    /**
     * 입력한 계좌로 1원 인증을 진행합니다.
     * 입력한 계좌의 예금주가 본인인증 정보의 실명과 다를 경우, 예외가 발생합니다.
     *
     * @param requestDto 은행 코드, 계좌 번호
     * @return 인증 코드
     */
    public String certifyTransfer(AccountCertifyTransferDto requestDto) {
        if (checkAccountOwner(requestDto)) {
            return accountSystem.certifyTransfer(requestDto.getOrganizationCode(), requestDto.getAccountNumbers());
        }
        throw new CNotOwnAccountException();
    }

}
