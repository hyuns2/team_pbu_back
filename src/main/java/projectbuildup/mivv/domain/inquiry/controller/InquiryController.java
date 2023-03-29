package projectbuildup.mivv.domain.inquiry.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.inquiry.dto.InquiryDto;
import projectbuildup.mivv.domain.inquiry.entity.InquiryEntity;
import projectbuildup.mivv.domain.inquiry.service.InquiryService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "[5-1. Inquiry]", description = "문의와 관련된 API입니다.")
@RequestMapping("/api/inquiry")
public class InquiryController {

    private final InquiryService service;

    @Operation(summary = "문의 등록", description = "사용자가 문의를 등록합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/inquiry-register")
    public ResponseEntity<?> inquiryRegister(@AuthenticationPrincipal User user, @RequestBody InquiryDto.InquiryRegisterDto dto) {
        InquiryEntity entity = InquiryDto.InquiryRegisterDto.toEntity(dto);
        entity.setUser(user);

        service.inquiryRegister(entity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //어드민 권한 설정 필요, hasRole('USER')로 테스트함
    @Operation(summary = "답변 등록", description = "관리자가 답변을 등록합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/answer-register")
    public ResponseEntity<?> answerRegister(@AuthenticationPrincipal User user, @RequestBody InquiryDto.AnswerRequestDto dto) {
        InquiryEntity entity = InquiryDto.AnswerRequestDto.toEntity(dto);
        entity.setUser(user);

        service.answerRegister(entity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}