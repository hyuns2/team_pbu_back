package projectbuildup.mivv.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.user.entity.PasswordChangeLink;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.PasswordChangeLinkRepository;
import projectbuildup.mivv.global.error.exception.CResourceNotFoundException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PasswordChangeLinkGenerator {
    private final PasswordChangeLinkRepository passwordChangeLinkRepository;

    @Value("${api.password-change}")
    String PASSWORD_CHANGE_API;

    /**
     * 사용자의 id로 비밀번호 변경 링크를 생성합니다.
     * 링크는 30분간 레디스에 저장됩니다.
     *
     * @param user 사용자
     * @return 비밀번호 변경 링크의 URI
     */
    public String createLink(User user) {
        PasswordChangeLink passwordChangeLink = new PasswordChangeLink(user);
        passwordChangeLinkRepository.save(passwordChangeLink);
        return PASSWORD_CHANGE_API + passwordChangeLink.makeQueryUri();
    }

    /**
     * 링크의 유효성을 검사합니다.
     * 레디스에 링크가 아직 존재하면, true를 반환합니다.
     *
     * @param serialNumber 링크의 시리얼 번호
     * @return true/false
     */
    public boolean validateLink(Long userId, String serialNumber) {
        Optional<PasswordChangeLink> passwordChangeLinkOptional = passwordChangeLinkRepository.findBySerialNumber(serialNumber);
        if (passwordChangeLinkOptional.isPresent() && passwordChangeLinkOptional.get().getUserId().equals(userId)){
            passwordChangeLinkRepository.delete(passwordChangeLinkOptional.get());
            return true;
        }
        return false;
    }
}
