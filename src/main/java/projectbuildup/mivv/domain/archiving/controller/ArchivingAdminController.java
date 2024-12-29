package projectbuildup.mivv.domain.archiving.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.archiving.dto.ArchivingResponse;
import projectbuildup.mivv.domain.archiving.dto.ArchivingRequest;
import projectbuildup.mivv.domain.archiving.entity.CardType;
import projectbuildup.mivv.domain.archiving.service.ArchivingAdminService;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "[Archiving_Admin]", description = "아카이빙_관리자와 관련된 API입니다.")
@RequestMapping("/api/archiving-admin")
public class ArchivingAdminController {
    private final ArchivingAdminService archivingAdminService;

    @Operation(summary = "절약 카드 생성 및 수정", description = "관리자가 절약 카드를 생성 또는 수정합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/saving-card", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> putSavingCard(@RequestParam(required = false) Long cardId, @Valid @ModelAttribute ArchivingRequest.SavingCardDto dto) throws IOException {
        archivingAdminService.putSavingCard(cardId, dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "소비 카드 생성 및 수정", description = "관리자가 소비 카드를 생성 또는 수정합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/coupon-card", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> putCouponCard(@RequestParam(required = false) Long cardId, @Valid @ModelAttribute ArchivingRequest.CouponCardDto dto) throws IOException {
        archivingAdminService.putCouponCard(cardId, dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "일반 카드 생성 및 수정", description = "관리자가 일반 카드를 생성 또는 수정합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/general-card", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> putGeneralCard(@RequestParam(required = false) Long cardId, @Valid @ModelAttribute ArchivingRequest.GeneralCardDto dto) throws IOException {
        archivingAdminService.putGeneralCard(cardId, dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "카드 삭제", description = "관리자가 카드를 삭제합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/card/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable("id") Long id) {
        archivingAdminService.deleteCard(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "카드 단건 조회", description = "관리자가 전체 카드 중 하나를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/card/{id}")
    public ResponseEntity<?> retrieveCard(@PathVariable("id") Long id) {
        ArchivingResponse.CardDto responseDto = archivingAdminService.retrieveCard(id);

        return ResponseEntity.ok().body(responseDto);
    }

    @Operation(summary = "카드 전체 및 조건별 조회", description = "관리자가 카드를 전체 또는 조건별로 조회합니다. (null 입력시, 전체조회)")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/cards")
    public ResponseEntity<?> retrieveCards(@RequestParam(required = false) CardType cardType) {
        List<ArchivingResponse.CardDto> responseDto = archivingAdminService.retrieveCards(cardType);

        return ResponseEntity.ok().body(responseDto);
    }

    @Operation(summary = "일반 카드 할당", description = "엑셀파일 주의: 한 행에 [이름, 전화번호]만 기입 & 반드시 모든 셀서식 텍스트로 설정")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/assign/general-cards", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> assignGeneralCards(@Valid @ModelAttribute ArchivingRequest.AssignGeneralCardDto dto, HttpServletResponse response) throws IOException {
        archivingAdminService.assignGeneralCards(dto, response);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
