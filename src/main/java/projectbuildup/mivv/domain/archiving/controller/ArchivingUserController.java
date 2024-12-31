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
import projectbuildup.mivv.domain.archiving.dto.ArchivingResponse;
import projectbuildup.mivv.domain.archiving.entity.CardType;
import projectbuildup.mivv.domain.archiving.service.ArchivingUserService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

import java.util.List;

@RestController
@Tag(name = "[Archiving_User]", description = "아카이빙_사용자와 관련된 API입니다.")
@RequestMapping("/api/archiving-user")
@RequiredArgsConstructor
public class ArchivingUserController {
    private final ArchivingUserService archivingUserService;

    @Operation(summary = "사용자의 신규 카드 조회", description = "사용자가 보유한 신규 카드 전체를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/new-cards")
    public ResponseEntity<?> retrieveNewCards(@AuthenticationPrincipal User user) {
        List<ArchivingResponse.UserCardDto> responseDto = archivingUserService.retrieveNewCards(user);

        return ResponseEntity.ok().body(responseDto);
    }

    @Operation(summary = "사용자 카드의 신규 여부 갱신", description = "사용자가 보유한 신규 카드를 신규가 아닌 카드로 갱신합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/new-cards")
    public ResponseEntity<?> updateCardToNotNew(@AuthenticationPrincipal User user) {
        archivingUserService.updateCardToNotNew(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "사용자의 전체 및 조건별 카드 조회", description = "조건을 선택해, 사용자가 보유한 해당하는 카드들을 조회합니다. (null 입력시, 전체조회)")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/cards")
    public ResponseEntity<?> retrieveUserCards(@AuthenticationPrincipal User user, @RequestParam CardType cardType) {
        List<ArchivingResponse.AllCardSummaryAndUserCardDto> responseDto = archivingUserService.retrieveUserCards(user, cardType);

        return ResponseEntity.ok().body(responseDto);
    }
}
