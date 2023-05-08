package projectbuildup.mivv.domain.archiving.controller;

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
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
import projectbuildup.mivv.domain.archiving.service.CouponArchivingService;
import projectbuildup.mivv.domain.archiving.service.GeneralArchivingService;
import projectbuildup.mivv.domain.archiving.service.NumericalArchivingService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "[6-1. Archiving]", description = "아카이빙과 관련된 API입니다.")
@RequestMapping("/api/archiving")
public class ArchivingController {

    private final NumericalArchivingService nService;
    private final GeneralArchivingService gService;
    private final CouponArchivingService cService;

    @Operation(summary = "수치 조건의 카드 생성", description = "관리자가 수치 조건의 카드를 생성합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/admin/numerical-card", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createNumericalConditionCard(@AuthenticationPrincipal User user, @Valid @ModelAttribute("createNumericalCards") ArchivingDto.createNumericalCardRequestDto dto) throws IOException {
        nService.createNumericalConditionCard(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "일반 조건의 카드 생성", description = "관리자가 일반 조건의 카드를 생성합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/admin/general-card", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createGeneralConditionCard(@AuthenticationPrincipal User user, @Valid @ModelAttribute("createGeneralCards") ArchivingDto.createGeneralCardRequestDto dto) throws IOException {
        gService.createGeneralConditionCard(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "쿠폰 발급 조건의 카드 생성", description = "관리자가 쿠폰 발급 조건의 카드를 생성합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/admin/coupon-card", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCouponConditionCard(@AuthenticationPrincipal User user, @Valid @ModelAttribute("createCouponCards") ArchivingDto.createCouponCardRequestDto dto) throws IOException {
        cService.createCouponConditionCard(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "수치 조건의 카드 수정", description = "관리자가 수치 조건의 카드를 수정합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "/admin/numerical-card/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateNumericalConditionCard(@AuthenticationPrincipal User user, @PathVariable("id") Long id, @Valid @ModelAttribute("updateNumericalCards") ArchivingDto.updateNumericalCardRequestDto dto) throws IOException {
        nService.updateNumericalConditionCard(id, dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "일반 조건의 카드 수정", description = "관리자가 일반 조건의 카드를 수정합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "/admin/general-card/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateGeneralConditionCard(@AuthenticationPrincipal User user, @PathVariable("id") Long id, @Valid @ModelAttribute("updateGeneralCards") ArchivingDto.updateGeneralCardRequestDto dto) throws IOException {
        gService.updateGeneralConditionCard(id, dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "쿠폰 발급 조건의 카드 수정", description = "관리자가 쿠폰 발급 조건의 카드를 수정합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "/admin/coupon-card/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCouponConditionCard(@AuthenticationPrincipal User user, @PathVariable("id") Long id, @Valid @ModelAttribute("updateCouponCards") ArchivingDto.updateCouponCardRequestDto dto) throws IOException {
        cService.updateCouponConditionCard(id, dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "카드 삭제", description = "관리자가 카드를 삭제합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/card/{id}")
    public ResponseEntity<?> deleteCard(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        gService.deleteCard(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "전체 카드 단건 조회", description = "관리자가 전체 카드 중 하나를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/card/{id}")
    public ResponseEntity<?> retrieveCard(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        ArchivingDto.CardResponseDto responseDto = gService.retrieveCard(id);

        return ResponseEntity.ok().body(responseDto);
    }

    @Operation(summary = "전체 카드 전체 조회", description = "관리자가 조건 상관없이 카드 전체를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/cards")
    public ResponseEntity<?> retrieveCards(@AuthenticationPrincipal User user) {
        List<ArchivingDto.CardResponseDto> responseDto = gService.retrieveCards();

        return ResponseEntity.ok().body(responseDto);
    }

    @Operation(summary = "사용자의 모든 카드 조회", description = "사용자가 보유한 카드 전체를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cards")
    public ResponseEntity<?> retrieveUserCard(@AuthenticationPrincipal User user) {
        List<ArchivingDto.UserCardResponseDto> responseDto = gService.retrieveUserCards(user);

        return ResponseEntity.ok().body(responseDto);
    }

    @Operation(summary = "수치 조건의 카드 할당", description = "수치 조건에 만족하는지 체크하고, 만족하면 사용자는 카드를 부여받습니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/assign/numerical-cards")
    public ResponseEntity<?> assignNumericalConditionCards(@AuthenticationPrincipal User user) {
        nService.assignNumericalConditionCards(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "일반 조건의 카드 할당", description = "관리자가 특정 조건을 달성한 사용자에게 해당하는 카드를 부여합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/assign/general-cards", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> assignGeneralConditionCards(@AuthenticationPrincipal User user, @ModelAttribute("assignGeneralCards") ArchivingDto.AssignGeneralCardsRequestDto dto) throws IOException {
        gService.assignGeneralConditionCards(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
