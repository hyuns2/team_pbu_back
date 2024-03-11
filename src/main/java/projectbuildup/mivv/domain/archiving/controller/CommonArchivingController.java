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
import projectbuildup.mivv.domain.archiving.entity.CardType;
import projectbuildup.mivv.domain.archiving.service.CardArchivingService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "[Archiving_Common]", description = "아카이빙_공통처리와 관련된 API입니다.")
@RequestMapping("/api/archiving")
public class CommonArchivingController {
    private final CardArchivingService cardArchivingService;

    @Operation(summary = "카드 삭제", description = "관리자가 카드를 삭제합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/card/{id}")
    public ResponseEntity<?> deleteCard(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        cardArchivingService.deleteCard(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "전체 카드 단건 조회", description = "관리자가 전체 카드 중 하나를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/card/{id}")
    public ResponseEntity<?> retrieveCard(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        ArchivingDto.CardResponseDto responseDto = cardArchivingService.retrieveCard(id);

        return ResponseEntity.ok().body(responseDto);
    }

    @Operation(summary = "전체 카드 전체 조회", description = "관리자가 조건 상관없이 카드 전체를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/cards")
    public ResponseEntity<?> retrieveCards(@AuthenticationPrincipal User user) {
        List<ArchivingDto.CardResponseDto> responseDto = cardArchivingService.retrieveCards();

        return ResponseEntity.ok().body(responseDto);
    }

    @Operation(summary = "사용자의 신규 카드 조회", description = "사용자가 보유한 신규 카드 전체를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/new-cards")
    public ResponseEntity<?> retrieveNewUserCards(@AuthenticationPrincipal User user) {
        List<ArchivingDto.UserCardResponseDto> responseDto = cardArchivingService.retrieveNewUserCards(user);

        return ResponseEntity.ok().body(responseDto);
    }

    @Operation(summary = "사용자 카드의 신규 여부 갱신", description = "사용자가 보유한 신규 카드를 신규가 아닌 카드로 갱신합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/new-cards")
    public ResponseEntity<?> updateCardToNotNew(@AuthenticationPrincipal User user) {
        cardArchivingService.updateCardToNotNew(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "사용자의 조건별 카드 조회", description = "조건을 선택해, 사용자가 보유한 해당 조건 카드 전체를 조회합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "액세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cards/{card-type}")
    public ResponseEntity<?> retrieveUserCards(@AuthenticationPrincipal User user, @PathVariable(value="card-type")CardType cardType) {
        List<ArchivingDto.CardAndUserCardResponseDto> responseDto = cardArchivingService.retrieveUserCards(user, cardType);

        return ResponseEntity.ok().body(responseDto);
    }

}
