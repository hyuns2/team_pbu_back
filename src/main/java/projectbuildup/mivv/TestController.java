package projectbuildup.mivv;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
}
