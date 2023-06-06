package projectbuildup.mivv;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.auth.repository.IdentityVerificationRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

@Tag(name = "[9-1.Test]", description = "테스트 컨트롤러")
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final UserRepository userRepository;
    private final IdentityVerificationRepository identityVerificationRepository;

    @ResponseBody
    @Operation(summary = "", description = "")
    @PostMapping("")
    public ResponseEntity<?> test() {
        User user = userRepository.findById(2L).orElseThrow(CUserNotFoundException::new);
        userRepository.delete(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "JSP 테스트", description = "")
    @GetMapping("/jsp")
    public String jspTest() {
        return "index";
    }

    @Operation(summary = "JSP KG 이니시스 요청 테스트", description = "")
    @GetMapping("/jsp/request")
    public String KgRequest() {
        return "request";
    }

    @Operation(summary = "JSP KG 이니시스 성공 테스트", description = "")
    @PostMapping("/jsp/success")
    public void KgSuccess(HttpServletRequest request, HttpServletResponse response) {
        log.info("resultCode>>: {}", request.getParameter("resultCode"));
        log.info("resultMsg>>: {}", request.getParameter("resultMsg"));
        log.info("txId>>: {}", request.getParameter("txId"));
        log.info("호출됨~~~~~~~~~~~~성공");
    }

    @Operation(summary = "JSP KG 이니시스 실패 테스트", description = "")
    @GetMapping("/jsp/fail")
    public String KgFail() {
        return "fail";
    }

    @NoArgsConstructor
    @Getter
    @ToString
    static class RequestDto {
        String mid;
        String txId;
    }
}
