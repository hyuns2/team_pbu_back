package projectbuildup.mivv.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.email.entity.Email;
import projectbuildup.mivv.domain.email.service.EmailSender;
import projectbuildup.mivv.domain.user.dto.PasswordChangeDto;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CBadRequestException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;
import projectbuildup.mivv.global.utils.DeviceFinder;

@Service
@RequiredArgsConstructor
public class PasswordChanger {
    private final PasswordChangeLinkGenerator passwordChangeLinkGenerator;
    private final UserRepository userRepository;
    private final EmailSender emailSender;

    /**
     * 비밀번호 재설정 링크가 담긴 메일을 사용자의 이메일 주소로 전송합니다.
     *
     * @param user 사용자
     */
    public void sendChangeLink(User user) {
        String uri = passwordChangeLinkGenerator.createLink(user);
        Email resetLinkEmail = Email.createResetLinkEmail(user.getEmail(), uri);
        emailSender.sendMail(resetLinkEmail);
    }

    /**
     * 비밀번호 재설정 링크의 유효성을 판단합니다.
     *
     * @param userId       사용자 아이디넘버
     * @param serialNumber 링크 시리얼 넘버
     */
    public void confirmChangeLink(Long userId, String serialNumber) {
        if (!passwordChangeLinkGenerator.validateLink(userId, serialNumber)) {
            throw new CBadRequestException("링크가 만료되었거나, 인증에 실패하였습니다.");
        }
    }

    /**
     * 사용자의 비밀번호를 변경합니다.
     *
     * @param requestDto 변경할 비밀번호, 회원 아이디넘버
     */
    public void changePassword(PasswordChangeDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(CUserNotFoundException::new);
        user.changePassword(requestDto.getPassword());
        userRepository.save(user);
    }
}
