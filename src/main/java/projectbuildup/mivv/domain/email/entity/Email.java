package projectbuildup.mivv.domain.email.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Email {
    @Schema(description = "수신자")
    String destination;
    @Schema(description = "제목")
    String subject;
    @Schema(description = "내용")
    String message;

    public static Email createResetEmail(String destination, String uri){
        String str = "코드 : " + uri + "<br>";
        String subject = "MIVV 인앱 비밀번호 재설정 안내 메일입니다.";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<h2> MIVV 인앱 비밀번호 재설정 안내 </h2><br>");
        stringBuilder.append("<p>");
        stringBuilder.append("이 이메일은 비밀번호 재설정을 위해 발송된 메일입니다. <br>");
        stringBuilder.append("비밀번호 재설정을 진행하려면 아래의 코드를 앱에 입력해주세요 <br><br>");
        stringBuilder.append(str);
        stringBuilder.append("</p>");
        String message = stringBuilder.toString();
        return new Email(destination, subject, message);
    }
}
