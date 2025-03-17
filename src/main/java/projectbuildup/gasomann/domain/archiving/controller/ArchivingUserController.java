package projectbuildup.gasomann.domain.archiving.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.gasomann.domain.archiving.entity.CardType;
import projectbuildup.gasomann.domain.archiving.service.ArchivingUserService;
import projectbuildup.gasomann.domain.user.entity.User;
import projectbuildup.gasomann.global.constant.ExampleValue;
import projectbuildup.gasomann.global.constant.Header;

@RestController
@Tag(name = "[Archiving_User]", description = "아카이빙_사용자와 관련된 API입니다.")
@RequestMapping("/api/archiving-user")
@RequiredArgsConstructor
public class ArchivingUserController {
    private final ArchivingUserService archivingUserService;

    @Operation(summary = "사용자의 신규 카드 조회 및 신규 여부 갱신", description = "사용자가 보유한 신규 카드 전체를 조회하고, 해당 카드들의 신규 여부를 갱신합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/new-cards")
    public ResponseEntity<?> retrieveAndUpdateNewCards(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(archivingUserService.retrieveAndUpdateNewCards(user));
    }

    @Operation(summary = "사용자의 전체 및 조건별 카드 조회", description = "조건을 선택해, 사용자가 보유한 해당하는 카드들을 조회합니다. (null 입력시, 전체조회)")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/cards")
    public ResponseEntity<?> retrieveUserCards(@AuthenticationPrincipal User user, @RequestParam(required = false) CardType cardType) {
        return ResponseEntity.ok().body(archivingUserService.retrieveUserCards(user, cardType));
    }
}
