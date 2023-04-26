package projectbuildup.mivv.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.user.dto.PasswordChangeDto;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.service.PasswordChanger;
import projectbuildup.mivv.global.constant.DeviceType;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;
import projectbuildup.mivv.global.error.exception.CBadRequestException;
import projectbuildup.mivv.global.utils.DeviceFinder;

@Slf4j
@RestController
@Tag(name = "[2.User]", description = "회원과 관련된 API입니다.")
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final PasswordChanger passwordChanger;
    private final static String PASSWORD_CHANGE_APP_SCHEMA = "/change";
    public static final String LOCATION = "location";


    @Operation(summary = "비밀번호 재설정 링크 전송", description = "회원의 이메일 주소로 비밀번호 재설정 링크가 담긴 메일을 전송합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/password/change")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal User user) {
        passwordChanger.sendChangeLink(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(hidden = true, summary = "비밀번호 재설정 링크 확인", description = "사용자가 재설정 링크를 클릭 시, 실행되는 API입니다. 링크의 유효성을 확인하고, 비밀번호 재설정 화면으로 리다이렉트시킵니다.")
    @PreAuthorize("permitAll()")
    @GetMapping("/password/change")
    public ResponseEntity<Void> confirmChangeLink(@RequestParam("userId") Long userId, @RequestParam("sn") String serialNumber, HttpServletRequest request) {
        passwordChanger.confirmChangeLink(userId, serialNumber);
        DeviceType deviceType = DeviceFinder.findUserDevice(request);
        HttpHeaders headers = new HttpHeaders();
        headers.add(LOCATION, deviceType.getSchemaRoot() + PASSWORD_CHANGE_APP_SCHEMA);
        log.info("비밀번호 재설정 링크 확인 완료");
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @Operation(summary = "비밀번호 변경", description = "사용자의 비밀번호를 변경합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/password/change")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid PasswordChangeDto requestDto, @AuthenticationPrincipal User user) {
        requestDto.setUserId(user.getId());
        passwordChanger.changePassword(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}