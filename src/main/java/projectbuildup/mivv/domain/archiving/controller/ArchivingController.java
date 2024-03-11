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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
import projectbuildup.mivv.domain.archiving.service.CouponCardArchivingService;
import projectbuildup.mivv.domain.archiving.service.GeneralCardArchivingService;
import projectbuildup.mivv.domain.archiving.service.SavingCardArchivingService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Tag(name = "[Archiving_ByCondition]", description = "아카이빙_조건별처리와 관련된 API입니다.")
@RequestMapping("/api/archiving")
public class ArchivingController {

    private final SavingCardArchivingService savingCardArchivingService;
    private final GeneralCardArchivingService generalCardArchivingService;
    private final CouponCardArchivingService couponCardArchivingService;

    @Operation(summary = "절약 카드 생성", description = "관리자가 절약 카드를 생성합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/admin/remittance-card", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createSavingCard(@AuthenticationPrincipal User user, @Valid @ModelAttribute("createNumericalCards") ArchivingDto.createOrUpdateSavingCardRequestDto dto) throws IOException {
        savingCardArchivingService.createSavingCard(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "절약 카드 수정", description = "관리자가 절약 카드를 수정합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/admin/remittance-card/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateSavingCard(@AuthenticationPrincipal User user, @PathVariable("id") Long id, @Valid @ModelAttribute("updateRemittanceCards") ArchivingDto.createOrUpdateSavingCardRequestDto dto) throws IOException {
        savingCardArchivingService.updateSavingCard(id, dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "소비 카드 생성", description = "관리자가 소비 카드를 생성합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/admin/coupon-card", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCouponCard(@AuthenticationPrincipal User user, @Valid @ModelAttribute("createCouponCards") ArchivingDto.createOrUpdateCouponCardRequestDto dto) throws IOException {
        couponCardArchivingService.createCouponCard(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "소비 카드 수정", description = "관리자가 소비 카드를 수정합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/admin/coupon-card/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCouponCard(@AuthenticationPrincipal User user, @PathVariable("id") Long id, @Valid @ModelAttribute("updateCouponCards") ArchivingDto.createOrUpdateCouponCardRequestDto dto) throws IOException {
        couponCardArchivingService.updateCouponCard(id, dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "일반 카드 생성", description = "관리자가 일반 카드를 생성합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/admin/general-card", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createGeneralCard(@AuthenticationPrincipal User user, @Valid @ModelAttribute("createGeneralCards") ArchivingDto.createOrUpdateGeneralCardRequestDto dto) throws IOException {
        generalCardArchivingService.createGeneralCard(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "일반 카드 수정", description = "관리자가 일반 카드를 수정합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/admin/general-card/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateGeneralCard(@AuthenticationPrincipal User user, @PathVariable("id") Long id, @Valid @ModelAttribute(name="updateGeneralCards") ArchivingDto.createOrUpdateGeneralCardRequestDto dto) throws IOException {
        generalCardArchivingService.updateGeneralCard(id, dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "일반 카드 할당", description = "관리자가 특정 조건을 달성한 사용자에게 해당하는 카드를 부여합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/assign/general-cards", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> assignGeneralConditionCards(@AuthenticationPrincipal User user, @ModelAttribute("assignGeneralCards") ArchivingDto.AssignGeneralCardsRequestDto dto, HttpServletResponse response) throws IOException {
        generalCardArchivingService.assignGeneralCards(dto, response);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
