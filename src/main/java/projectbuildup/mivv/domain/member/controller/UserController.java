package projectbuildup.mivv.domain.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "[2-1.Member]", description = "회원과 관련된 API입니다.")
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    
}