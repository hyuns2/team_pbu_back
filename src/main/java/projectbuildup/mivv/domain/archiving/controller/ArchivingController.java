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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectbuildup.mivv.domain.archiving.dto.ArchivingDto;
import projectbuildup.mivv.domain.archiving.service.ArchivingService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

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
    @PostMapping()
    public ResponseEntity<?> createNumericalConditionCard(@AuthenticationPrincipal User user, @RequestBody ArchivingDto.NumericalConditionCardRequestDto dto) {
        service.registerNumericalConditionCard(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
