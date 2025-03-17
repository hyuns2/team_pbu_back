package projectbuildup.gasomann;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import projectbuildup.gasomann.domain.auth.repository.IdentityVerificationRepository;
import projectbuildup.gasomann.domain.auth.service.IdentityVerificationService;
import projectbuildup.gasomann.domain.user.entity.User;
import projectbuildup.gasomann.domain.user.repository.UserRepository;
import projectbuildup.gasomann.global.error.exception.CUserNotFoundException;

@Tag(name = "[9-1.Test]", description = "테스트 컨트롤러")
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final UserRepository userRepository;
    private final IdentityVerificationRepository identityVerificationRepository;
    private final IdentityVerificationService identityVerificationService;

    @ResponseBody
    @Operation(summary = "", description = "")
    @PostMapping("")
    public ResponseEntity<?> test() {
        User user = userRepository.findById(2L).orElseThrow(CUserNotFoundException::new);
        userRepository.delete(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "JSP KG 이니시스 요청 테스트", description = "")
    @GetMapping("/jsp/request")
    public String KgRequest() {
        return "request";
    }

    @NoArgsConstructor
    @Getter
    @ToString
    static class RequestDto {
        String mid;
        String txId;
    }
}
