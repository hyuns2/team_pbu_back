package projectbuildup.mivv.domain.likes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.likes.entity.LikesCategory;
import projectbuildup.mivv.domain.likes.service.LikesService;
import projectbuildup.mivv.domain.shorts.dto.ShortsDto;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.worthyConsumption.dto.response.WorthyConsumptionResponseDto;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/")
@RestController
public class LikesController {
    private final LikesService likesService;
    /**
     * 가치소비를 찜합니다.
     *
     */
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "가치소비 찜 ", description = "가치소비를 찜합니다.")
    @PostMapping("worthyConsumptions/{worthyConsumptionId}/likes")
    public ResponseEntity<HttpStatus> addLikesWorthyConsumption(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @AuthenticationPrincipal User user){
        likesService.addLikes(user.getId(), worthyConsumptionId, LikesCategory.WORTHY_CONSUMPTION);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    /**
     * 찜한 가치소비를 해제합니다.
     *
     */
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "가치소비 찜 해제 ", description = "찜한 가치소비를 해제합니다.")
    @DeleteMapping("worthyConsumptions/{worthyConsumptionId}/likes")
    public ResponseEntity<HttpStatus> deleteLikesWorthyConsumption(@PathVariable(name = "worthyConsumptionId") Long worthyConsumptionId, @AuthenticationPrincipal User user){
        likesService.deleteLikes(user.getId(), worthyConsumptionId, LikesCategory.WORTHY_CONSUMPTION);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
     * 쇼츠를 찜합니다.
     * @param shortsId
     * @param user
     * @return
     */
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "쇼츠 찜", description = "쇼츠를 찜합니다.")
    @PostMapping("shorts/{shortsId}/likes")
    public ResponseEntity<HttpStatus> addLikesShorts(@PathVariable(name = "shortsId") Long shortsId, @AuthenticationPrincipal User user){
        LikesCategory likesCategory = likesService.findShortsCategory(shortsId);
        likesService.addLikes(user.getId(), shortsId, likesCategory);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 찜한 쇼츠를 삭제합니다.
     * @param shortsId
     * @param user
     * @return
     */
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "쇼츠 찜 해제", description = "찜한 쇼츠를 해제합니다.")
    @DeleteMapping("shorts/{shortsId}/likes")
    public ResponseEntity<HttpStatus> deleteLikes(@PathVariable(name = "shortsId") Long shortsId, @AuthenticationPrincipal User user){
        LikesCategory likesCategory = likesService.findShortsCategory(shortsId);
        likesService.deleteLikes(user.getId(), shortsId, likesCategory);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "교육 쇼츠 조회", description = "찜한 교육 쇼츠를 조회합니다.")
    @GetMapping("likes/shorts/edu")
    public ResponseEntity<List<ShortsDto.shortsResponse>> getUserLikesEduShorts(@AuthenticationPrincipal User user){
        List<ShortsDto.shortsResponse> userLikesEduShortsDtoList = likesService.getUserLikesEduShorts(user.getId());
        return new ResponseEntity<>(userLikesEduShortsDtoList, HttpStatus.OK);
    }
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "찜한 절약 쇼츠 조회", description = "찜한 절약 쇼츠를 조회합니다.")
    @GetMapping("likes/shorts/saving")
    public ResponseEntity<List<ShortsDto.shortsResponse>> getUserLikesSavingShorts(@AuthenticationPrincipal User user){
        List<ShortsDto.shortsResponse> userLikesSavingShortsDtoList = likesService.getUserLikesSavingShorts(user.getId());
        return new ResponseEntity<>(userLikesSavingShortsDtoList, HttpStatus.OK);
    }
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "찜한 가치소비 조회", description = "찜한 가치소비를 조회합니다.")
    @GetMapping("likes/worthyConsumptions")
    public ResponseEntity<List<WorthyConsumptionResponseDto.ReadSummaryResponse>> getUserLikesWorthyConsumptionShorts(@AuthenticationPrincipal User user){
        List<WorthyConsumptionResponseDto.ReadSummaryResponse> userLikesWorthyConsumptionDtoList = likesService.getUserLikesWorthyConsumption(user.getId());
        return new ResponseEntity<>(userLikesWorthyConsumptionDtoList, HttpStatus.OK);
    }

}
