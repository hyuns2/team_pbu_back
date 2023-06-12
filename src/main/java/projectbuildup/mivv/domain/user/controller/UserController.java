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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.account.entity.UserState;
import projectbuildup.mivv.domain.user.dto.PasswordDto;
import projectbuildup.mivv.domain.user.dto.ProfileDto;
import projectbuildup.mivv.domain.user.dto.UserStateRequestDto;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.service.PasswordChanger;
import projectbuildup.mivv.domain.user.service.UserService;
import projectbuildup.mivv.global.constant.DeviceType;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;
import projectbuildup.mivv.global.utils.DeviceFinder;

import java.io.IOException;

@Slf4j
@RestController
@Tag(name = "[2.User]", description = "회원과 관련된 API입니다.")
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final PasswordChanger passwordChanger;
    private final UserService userService;

    @Operation(summary = "비밀번호 재설정", description = "회원의 이메일 주소로 비밀번호 재설정 코드를 전송합니다.")
    @PostMapping("/password/change")
    public ResponseEntity<PasswordDto.Response> changePassword(@RequestBody PasswordDto.SendRequest requestDto) {
        PasswordDto.Response response = passwordChanger.sendMail(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "비밀번호 변경", description = "사용자의 비밀번호를 변경합니다.")
    @PatchMapping("/password/change")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid PasswordDto.ChangeRequest requestDto, @AuthenticationPrincipal User user) {
        requestDto.setUserId(user.getId());
        passwordChanger.changePassword(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "프로필 조회", description = "사용자의 프로필 정보를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/profile")
    public ResponseEntity<ProfileDto.Response> getProfile(@AuthenticationPrincipal User user) {
        ProfileDto.Response responseDto = userService.getProfile(user.getId());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "프로필 수정", description = "사용자의 프로필 정보를 수정합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateProfile(@Valid @ModelAttribute("updateRequest") ProfileDto.UpdateRequest requestDto, @AuthenticationPrincipal User user) throws IOException {
        userService.updateProfile(user.getId(), requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "사용자 상태 조회", description = "SIGNUPED / REGISTERED / NEW 중 하나를 리턴합니다. ")
    @PostMapping(value = "/state")
    public ResponseEntity<UserState> getUserState(@RequestBody UserStateRequestDto requestDto) {
        UserState userState = userService.getUserState(requestDto.getVerificationCode());
        return new ResponseEntity<>(userState, HttpStatus.OK);
    }
}