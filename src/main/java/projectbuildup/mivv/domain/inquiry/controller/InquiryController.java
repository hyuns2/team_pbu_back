package projectbuildup.mivv.domain.inquiry.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.inquiry.dto.InquiryDto;
import projectbuildup.mivv.domain.inquiry.service.InquiryService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "[5-1. Inquiry]", description = "문의와 관련된 API입니다.")
@RequestMapping("/api/inquiry")
public class InquiryController {

    private final InquiryService service;

    @Operation(summary = "사용자 문의 등록", description = "사용자가 문의를 등록합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> createInquiry(@AuthenticationPrincipal User user, @Valid @RequestBody InquiryDto.InquiryRequestDto dto) {
        service.registerInquiry(dto, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "관리자 답변 등록", description = "관리자가 답변을 등록합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/answer")
    public ResponseEntity<?> createAnswer(@AuthenticationPrincipal User user, @Valid @RequestBody InquiryDto.AnswerRequestDto dto) {
        service.registerAnswer(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "사용자 문의 전체 조회", description = "사용자가 본인의 문의 전체를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/inquiries")
    public ResponseEntity<?> retrieveForUser(@AuthenticationPrincipal User user) {
        List<InquiryDto.InquiryResponseDto> resultDto = service.retrieveForUser(user);

        return ResponseEntity.ok().body(resultDto);
    }

    @Operation(summary = "관리자 문의 전체 조회", description = "관리자가 시간순으로 문의를 전체 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/inquiries")
    public ResponseEntity<?> retrieveForAdmin(@AuthenticationPrincipal User user) {
        List<InquiryDto.InquiryResponseDto> resultDto = service.retrieveForAdmin();

        return ResponseEntity.ok().body(resultDto);
    }

    @Operation(summary = "문의 상세 조회", description = "문의의 상세내역을 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveDetails(@AuthenticationPrincipal User user, @PathVariable Long id) {
        InquiryDto.InquiryResponseDto resultDto = service.retrieveDetails(id);

        return ResponseEntity.ok().body(resultDto);
    }

    @Operation(summary = "사용자 문의 삭제", description = "사용자가 문의를 삭제합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInquiry(@AuthenticationPrincipal User user, @PathVariable Long id) {
        service.deleteInquiry(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "관리자 답변 삭제", description = "관리자가 답변을 삭제합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/answer/{id}")
    public ResponseEntity<?> deleteAnswer(@AuthenticationPrincipal User user, @PathVariable Long id) {
        service.deleteAnswer(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}