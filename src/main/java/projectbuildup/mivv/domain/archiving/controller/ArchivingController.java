package projectbuildup.mivv.domain.archiving.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
import projectbuildup.mivv.domain.archiving.service.ArchivingService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "[6-1. Archiving]", description = "아카이빙과 관련된 API입니다.")
@RequestMapping("/api/archiving")
public class ArchivingController {

    private final ArchivingService service;

    //어드민 권한 설정 필요, hasRole('USER')로 테스트함
    @Operation(summary = "수치 조건의 카드 생성", description = "관리자가 수치 조건의 카드를 생성합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/admin/card")
    public ResponseEntity<?> createNumericalConditionCard(@AuthenticationPrincipal User user, @RequestBody ArchivingDto.createNumericalConditionCardRequestDto dto) {
        service.createNumericalConditionCard(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //어드민 권한 설정 필요, hasRole('USER')로 테스트함
    @Operation(summary = "수치 조건의 카드 수정", description = "관리자가 수치 조건의 카드를 수정합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/admin/card/{id}")
    public ResponseEntity<?> updateNumericalConditionCard(@AuthenticationPrincipal User user, @PathVariable("id") Long id, @RequestBody ArchivingDto.updateNumericalConditionCardRequestDto dto) {
        service.updateNumericalConditionCard(id, dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //어드민 권한 설정 필요, hasRole('USER')로 테스트함
    @Operation(summary = "카드 삭제", description = "관리자가 카드를 삭제합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/admin/card/{id}")
    public ResponseEntity<?> deleteNumericalConditionCard(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        service.deleteNumericalConditionCard(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //어드민 권한 설정 필요, hasRole('USER')로 테스트함
    @Operation(summary = "카드 단건 조회", description = "관리자가 카드 하나를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/admin/card/{id}")
    public ResponseEntity<?> retrieveNumericalConditionCard(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        ArchivingDto.NumericalConditionCardResponseDto responseDto = service.retrieveNumericalConditionCard(id);

        return ResponseEntity.ok().body(responseDto);
    }

    //어드민 권한 설정 필요, hasRole('USER')로 테스트함
    @Operation(summary = "카드 전체 조회", description = "관리자가 카드 전체를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/admin/cards")
    public ResponseEntity<?> retrieveNumericalConditionCards(@AuthenticationPrincipal User user) {
        List<ArchivingDto.NumericalConditionCardResponseDto> responseDto = service.retrieveNumericalConditionCards();

        return ResponseEntity.ok().body(responseDto);
    }

    @Operation(summary = "사용자의 모든 카드 조회", description = "사용자가 보유한 카드 전체를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cards")
    public ResponseEntity<?> retrieveUserCard(@AuthenticationPrincipal User user) {
        List<ArchivingDto.UserCardResponseDto> responseDto = service.retrieveUserCards(user);

        return ResponseEntity.ok().body(responseDto);
    }

    @Operation(summary = "카드 할당", description = "사용자에게 조건에 맞는 카드를 할당합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/assign")
    public ResponseEntity<?> assignNumericalConditionCards(@AuthenticationPrincipal User user) {
        service.assignNumericalConditionCards(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
