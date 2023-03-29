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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @PostMapping("/inquiry-register")
    public ResponseEntity<?> registerInquiry(@AuthenticationPrincipal User user, @RequestBody InquiryDto.InquiryRequestDto dto) {
        InquiryEntity entity = InquiryDto.InquiryRequestDto.toEntity(dto);
        entity.setUser(user);

        service.registerInquiry(entity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //어드민 권한 설정 필요, hasRole('USER')로 테스트함
    @Operation(summary = "관리자 답변 등록", description = "관리자가 답변을 등록합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/answer-register")
    public ResponseEntity<?> registerAnswer(@AuthenticationPrincipal User user, @RequestBody InquiryDto.AnswerRequestDto dto) {
        InquiryEntity entity = InquiryDto.AnswerRequestDto.toEntity(dto);
        entity.setUser(user);

        service.registerAnswer(entity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "사용자 문의 전체 조회", description = "사용자가 본인의 문의 전체를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/retrieve-user")
    public ResponseEntity<?> retrieveForUser(@AuthenticationPrincipal User user) {
        InquiryEntity entity = new InquiryEntity();
        entity.setUser(user);

        List<InquiryEntity> resultEntity = service.retrieveForUser(entity);

        List<InquiryDto.InquiryResponseDto> resultDto = resultEntity.stream().map(InquiryDto.InquiryResponseDto::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(resultDto);
    }

    //어드민 권한 설정 필요, hasRole('USER')로 테스트함
    @Operation(summary = "관리자 문의 전체 조회", description = "관리자가 시간순으로 문의를 전체 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/retrieve-admin")
    public ResponseEntity<?> retrieveForAdmin(@AuthenticationPrincipal User user) {

        List<InquiryEntity> resultEntity = service.retrieveForAdmin();

        List<InquiryDto.InquiryResponseDto> resultDto = resultEntity.stream().map(InquiryDto.InquiryResponseDto::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(resultDto);
    }

    @Operation(summary = "사용자 문의 단건 조회", description = "사용자가 본인의 문의의 상세내역 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/retrieve-details-user")
    public ResponseEntity<?> retrieveDetailsForUser(@AuthenticationPrincipal User user, @RequestBody InquiryDto.InquiryRequestDto dto) {
        InquiryEntity entity = InquiryDto.InquiryRequestDto.toEntity(dto);
        entity.setUser(user);

        InquiryEntity resultEntity = service.retrieveDetailsForUser(entity);

        InquiryDto.InquiryResponseDto resultDto = new InquiryDto.InquiryResponseDto(resultEntity);

        return ResponseEntity.ok().body(resultDto);
    }

    //어드민 권한 설정 필요, hasRole('USER')로 테스트함
    @Operation(summary = "관리자 문의 단건 조회", description = "관리자가 문의의 상세내역을 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/retrieve-details-admin")
    public ResponseEntity<?> retrieveDetailsForAdmin(@AuthenticationPrincipal User user, @RequestBody InquiryDto.SimpleRequestDto dto) {
        InquiryEntity entity = InquiryDto.SimpleRequestDto.toEntity(dto);
        entity.setUser(user);

        InquiryEntity resultEntity = service.retrieveDetailsForAdmin(entity);

        InquiryDto.InquiryResponseDto resultDto = new InquiryDto.InquiryResponseDto(resultEntity);

        return ResponseEntity.ok().body(resultDto);
    }

    @Operation(summary = "사용자 문의 삭제", description = "사용자가 문의를 삭제합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/delete")
    public ResponseEntity<?> deleteInquiry(@AuthenticationPrincipal User user, @RequestBody InquiryDto.InquiryRequestDto dto) {
        InquiryEntity entity = InquiryDto.InquiryRequestDto.toEntity(dto);
        entity.setUser(user);

        service.deleteInquiry(entity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}