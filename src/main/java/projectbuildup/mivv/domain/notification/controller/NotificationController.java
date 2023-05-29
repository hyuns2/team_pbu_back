package projectbuildup.mivv.domain.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.notification.dto.NotificationDto;
import projectbuildup.mivv.domain.notification.service.NotificationService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "[Notification]", description = "알림 페이지와 관련된 API입니다.")
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService service;

    @Operation(summary = "이벤트 알림글 생성", description = "관리자가 이벤트 알림글을 생성합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/event",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createEventNotification(@AuthenticationPrincipal User user, @Valid @ModelAttribute("createEvent") NotificationDto.NotificationRequestDto dto) throws IOException {
        service.createEventNotification(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "공지 알림글 생성", description = "관리자가 공지 알림글을 생성합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/notice", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createNoticeNotification(@AuthenticationPrincipal User user, @Valid @ModelAttribute("createEvent") NotificationDto.NotificationRequestDto dto) throws IOException {
        service.createNoticeNotification(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "알림글 삭제", description = "관리자가 알림글을 삭제합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        service.deleteNotification(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "알림글 전체 조회", description = "알림글 전체를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @GetMapping
    public ResponseEntity<?> retrieveEventNotifications(@AuthenticationPrincipal User user) {
        List<NotificationDto.NotificationResponseDto> responseDto = service.retrieveNotifications();

        return ResponseEntity.ok().body(responseDto);
    }

}
