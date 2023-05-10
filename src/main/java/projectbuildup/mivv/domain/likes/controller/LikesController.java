package projectbuildup.mivv.domain.likes.controller;

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
import projectbuildup.mivv.domain.likes.service.LikesService;
import projectbuildup.mivv.domain.shorts.dto.ShortsDto;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.worthyConsumption.dto.response.WorthyConsumptionResponseDto;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
@Tag(name = "[Likes]", description = "찜 관련 API입니다.")
public class LikesController {
    private final LikesService likesService;

    @Operation(summary = "가치소비 찜 ", description = "가치소비를 찜합니다.")
    @PostMapping("/worthyConsumptions/{worthyConsumptionId}/likes")
    public ResponseEntity<HttpStatus> addLikesWorthyConsumption(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @AuthenticationPrincipal User user){
        likesService.addLikesWorthyConsumption(user.getId(), worthyConsumptionId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "가치소비 찜 해제 ", description = "찜한 가치소비를 해제합니다.")
    @DeleteMapping("worthyConsumptions/{worthyConsumptionId}/likes")
    public ResponseEntity<HttpStatus> deleteLikesWorthyConsumption(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @AuthenticationPrincipal User user){
        likesService.deleteLikesWorthyConsumption(user.getId(), worthyConsumptionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "쇼츠 찜", description = "쇼츠를 찜합니다.")
    @PostMapping("shorts/{shortsId}/likes")
    public ResponseEntity<HttpStatus> addLikesShorts(@PathVariable(name = "shortsId") Long shortsId, @AuthenticationPrincipal User user){
        likesService.addLikesShorts(user.getId(), shortsId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "쇼츠 찜 해제", description = "찜한 쇼츠를 해제합니다.")
    @DeleteMapping("shorts/{shortsId}/likes")
    public ResponseEntity<HttpStatus> deleteLikes(@PathVariable(name = "shortsId") Long shortsId, @AuthenticationPrincipal User user){
        likesService.deleteLikesShorts(user.getId(), shortsId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "찜한 교육 쇼츠 조회", description = "찜한 교육 쇼츠를 모두 조회합니다.")
    @GetMapping("likes/shorts/edu")
    public ResponseEntity<List<ShortsDto.shortsResponse>> getUserLikesEduShorts(@AuthenticationPrincipal User user){
        List<ShortsDto.shortsResponse> userLikesEduShortsDtoList = likesService.getAllUserLikesEduShorts(user.getId());
        return new ResponseEntity<>(userLikesEduShortsDtoList, HttpStatus.OK);
    }
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "찜한 절약 쇼츠 조회", description = "찜한 절약 쇼츠를 모두 조회합니다.")
    @GetMapping("likes/shorts/saving")
    public ResponseEntity<List<ShortsDto.shortsResponse>> getUserLikesSavingShorts(@AuthenticationPrincipal User user){
        List<ShortsDto.shortsResponse> userLikesSavingShortsDtoList = likesService.getAllUserLikesSavingShorts(user.getId());
        return new ResponseEntity<>(userLikesSavingShortsDtoList, HttpStatus.OK);
    }
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "찜한 모든 쇼츠 조회", description = "찜한 쇼츠를 모두 조회합니다.")
    @GetMapping("likes/shorts")
    public ResponseEntity<List<ShortsDto.shortsResponse>> getUserLikesShorts(@AuthenticationPrincipal User user){
        List<ShortsDto.shortsResponse> userLikesSavingShortsDtoList = likesService.getAllUserLikesShorts(user.getId());
        return new ResponseEntity<>(userLikesSavingShortsDtoList, HttpStatus.OK);
    }
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "찜한 모든 가치소비 조회", description = "찜한 가치소비를 모두 조회합니다.")
    @GetMapping("likes/worthyConsumptions")
    public ResponseEntity<List<WorthyConsumptionResponseDto.ReadSummaryResponse>> getUserLikesWorthyConsumptionShorts(@AuthenticationPrincipal User user){
        List<WorthyConsumptionResponseDto.ReadSummaryResponse> userLikesWorthyConsumptionDtoList = likesService.getAllUserLikesWorthyConsumption(user.getId());
        return new ResponseEntity<>(userLikesWorthyConsumptionDtoList, HttpStatus.OK);
    }

}
