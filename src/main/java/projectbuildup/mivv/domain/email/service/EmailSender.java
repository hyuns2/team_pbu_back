package projectbuildup.mivv.domain.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.email.entity.Email;
import projectbuildup.mivv.global.error.exception.CInternalServerException;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSender {
    private final JavaMailSender javaMailSender;

    /**
     * 이메일을 전송합니다.
     *
     * @param email 이메일
     */
    public void sendMail(Email email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(email.getDestination());
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setText(email.getMessage(), true);
            javaMailSender.send(mimeMessage);
            log.info("이메일 전송 성공");
        } catch (MessagingException e) {
            log.info("이메일 전송 실패");
            throw new CInternalServerException();
        }
    }
}
