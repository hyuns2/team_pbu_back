package projectbuildup.mivv.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.global.error.exception.CInternalServerException;

import java.util.Random;
@Service
@RequiredArgsConstructor
public class AdminService {

    private final JavaMailSender javaMailSender;
    public String generateRandomString() {
        // 랜덤 문자열 생성 로직 작성
        // 예를 들어, 무작위로 영어와 숫자를 조합하여 문자열 생성하는 방법은 다양하게 구현할 수 있습니다.
        // 여기서는 8자리의 랜덤 문자열을 생성하는 방법을 예시로 보여드립니다.
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
    public void sendEmail(String recipientEmail, String content) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipientEmail);
            message.setSubject("관리자 페이지 접근 확인");
            message.setText(content);

            javaMailSender.send(message);
        }catch (MailException e){
            throw new CInternalServerException();
        }
    }

}
