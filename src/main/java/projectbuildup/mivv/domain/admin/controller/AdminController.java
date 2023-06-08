package projectbuildup.mivv.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import projectbuildup.mivv.domain.admin.service.AdminService;

import java.util.Random;
@RequiredArgsConstructor
@RestController
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/generate-random-string")
    public ResponseEntity<String> generateRandomString(@RequestParam("email") String email) {

        String randomString = adminService.generateRandomString();
        adminService.sendEmail(email, randomString);

        return ResponseEntity.ok(randomString);
    }


}
