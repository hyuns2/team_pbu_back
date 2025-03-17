package projectbuildup.gasomann.domain.archiving.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.gasomann.domain.archiving.dto.ArchivingRequest;
import projectbuildup.gasomann.domain.archiving.entity.CardType;
import projectbuildup.gasomann.domain.archiving.service.ArchivingAdminService;
import projectbuildup.gasomann.global.constant.ExampleValue;
import projectbuildup.gasomann.global.constant.Header;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Tag(name = "[Archiving_Admin]", description = "아카이빙_관리자와 관련된 API입니다.")
@RequestMapping("/api/archiving-admin")
public class ArchivingAdminController {
    private final ArchivingAdminService archivingAdminService;

    @Operation(summary = "카드 생성", description = """
            - 관리자가 카드를 생성합니다.
            - 생성 가능한 카드 종류와 발급 조건은 다음과 같습니다.
                1. 절약카드(Saving): n원 이상, m번 이상, k일 동안 챌린지를 통해 절약한 경우
                2. 쿠폰카드(Coupon): n번 째로, m개월 연속으로 쿠폰을 발급받은 경우
                3. 일반카드(General): 관리자가 직접 선정
            - 주의사항: 수치 조건의 경우, 0을 입력하면 무시됩니다.
            """)
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createCard(@RequestPart @Valid ArchivingRequest.CardCreateDto dto, @RequestPart @NotNull MultipartFile image) {
        archivingAdminService.createCard(dto, image);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "카드 수정", description = """
            - 관리자가 카드를 수정합니다.
            - 수정은 카드 종류별로 가능하고, 카드 종류는 수정할 수 없습니다.
            - 주의사항: 수치 조건이 수정된 경우, 수정 이후의 발급부터 해당 조건이 적용됩니다. 수정 이전의 발급은 그대로 유지됩니다.
            """)
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCard(@PathVariable Long id, @RequestPart @Valid ArchivingRequest.CardUpdateDto dto, @RequestPart MultipartFile image) throws IOException {
        archivingAdminService.updateCard(id, dto, image);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "카드 삭제", description = "관리자가 카드를 삭제합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id) {
        archivingAdminService.deleteCard(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "카드 단건 조회", description = "관리자가 전체 카드 중 하나를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveCard(@PathVariable Long id) {
        return ResponseEntity.ok().body(archivingAdminService.retrieveCard(id));
    }

    @Operation(summary = "카드 전체 및 조건별 조회", description = "관리자가 카드를 전체 또는 조건별로 조회합니다. (null 입력시, 전체조회)")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/cards")
    public ResponseEntity<?> retrieveCards(@RequestParam(required = false) CardType cardType) {
        return ResponseEntity.ok().body(archivingAdminService.retrieveCards(cardType));
    }

    @Operation(summary = "일반카드 할당", description = """
            - 관리자가 엑셀파일에 존재하는 유저들에게 일반카드를 할당합니다. 회원정보가 존재하지 않는 유저 정보는 다시 엑셀파일로 반환됩니다.
            - 주의사항: 반드시 모든 셀서식이 텍스트로 설정되고, 한 행당 유저 1명의 [이름, 전화번호] 순으로 기입된 엑셀파일이어야 정상처리 가능합니다.
            """)
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/assign/general-cards", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> assignGeneralCards(@Valid ArchivingRequest.GeneralCardAssignDto dto, HttpServletResponse response) throws IOException {
        archivingAdminService.assignGeneralCards(dto, response);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
