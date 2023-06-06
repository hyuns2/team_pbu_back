package projectbuildup.mivv;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectbuildup.mivv.domain.auth.repository.IdentityVerificationRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

@Tag(name = "[9-1.Test]", description = "테스트 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@Slf4j
public class TestController {
    private final UserRepository userRepository;
    private final IdentityVerificationRepository identityVerificationRepository;

    @Operation(summary="", description="")
    @PostMapping("")
    public ResponseEntity<?> test(){
        User user = userRepository.findById(2L).orElseThrow(CUserNotFoundException::new);
        userRepository.delete(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "JSP KG 이니시스 성공 테스트", description = "")
    @PostMapping("/jsp/success")
    public void KgSuccess(HttpServletRequest request, HttpServletResponse response) {
        log.info("resultCode>>: {}", request.getParameter("resultCode"));
        log.info("resultMsg>>: {}", request.getParameter("resultMsg"));
        log.info("txId>>: {}", request.getParameter("txId"));
        log.info("호출됨~~~~~~~~~~~~성공");
    }
}
