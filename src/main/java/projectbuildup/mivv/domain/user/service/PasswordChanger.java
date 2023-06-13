package projectbuildup.mivv.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.auth.repository.IdentityVerificationRepository;
import projectbuildup.mivv.domain.email.entity.Email;
import projectbuildup.mivv.domain.email.service.EmailSender;
import projectbuildup.mivv.domain.user.dto.PasswordDto;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CBadRequestException;
import projectbuildup.mivv.global.error.exception.CResourceNotFoundException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

@Service
@RequiredArgsConstructor
public class PasswordChanger {
    private final PasswordChangeLinkGenerator passwordChangeLinkGenerator;
    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private final IdentityVerificationRepository identityVerificationRepository;

    /**
     * 사용자의 이메일로 비밀번호 재설정 코드를 전송합니다.
     *
     * @param requestDto 본인인증 코드
     * @return 이메일로 발송된 랜덤 코드, 사용자 이메일
     */
    public PasswordDto.Response sendMail(PasswordDto.SendRequest requestDto) {
        IdentityVerification identityVerification = identityVerificationRepository.findByCode(requestDto.getVerificationCode()).orElseThrow(CResourceNotFoundException::new);
        User user = identityVerification.getUser();
        String code = RandomStringUtils.randomAlphabetic(8);
        Email resetEmail = Email.createResetEmail(user.getEmail(), code);
        emailSender.sendMail(resetEmail);
        return new PasswordDto.Response(code, user.getEmail());
    }

    /**
     * 사용자의 비밀번호를 변경합니다.
     *
     * @param requestDto 변경할 비밀번호, 회원 아이디넘버
     */
    public void changePassword(PasswordDto.ChangeRequest requestDto) {
        IdentityVerification identityVerification = identityVerificationRepository.findByCode(requestDto.getVerificationCode()).orElseThrow(CResourceNotFoundException::new);
        User user = identityVerification.getUser();
        user.changePassword(requestDto.getPassword());
        userRepository.save(user);
    }
}
