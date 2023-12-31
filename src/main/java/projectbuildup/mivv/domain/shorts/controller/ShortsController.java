package projectbuildup.mivv.domain.shorts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.mivv.domain.shorts.dto.ShortsDto;
import projectbuildup.mivv.domain.shorts.service.ShortsService;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.ExampleValue;
import projectbuildup.mivv.global.constant.Header;

import java.io.IOException;
import java.util.List;
@Tag(name = "[Shorts]", description = "쇼츠 관련 API입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/shorts")
public class ShortsController {
  private final ShortsService shortsService;
  @Operation(summary = "쇼츠 생성", description = "쇼츠를 생성합니다.")
  @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<HttpStatus> createShorts(@Valid @ModelAttribute("createShorts") ShortsDto.creatRequest shortsRequestDto) throws IOException {
      shortsService.createShorts(shortsRequestDto);
      return new ResponseEntity<>(HttpStatus.CREATED);
  }
  @Operation(summary = "쇼츠 단건 조회", description = "쇼츠 하나를 조회합니다.")
  @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
  @PreAuthorize("hasRole('USER')")
  @GetMapping("/{shortsId}")
  public ResponseEntity<ShortsDto.shortsResponse> getOneShorts(@PathVariable(name = "shortsId") Long shortsId, @AuthenticationPrincipal User user){
      ShortsDto.shortsResponse shortsResponseDto = shortsService.getOneShorts(shortsId, user.getId());
      return new ResponseEntity<>(shortsResponseDto, HttpStatus.OK);
  }
  @Operation(summary = "절약 쇼츠 조회", description = "절약 쇼츠를 모두 조회합니다.")
  @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
  @PreAuthorize("hasRole('USER')")
  @GetMapping("/saving")
  public ResponseEntity<List<ShortsDto.shortsResponse>> getAllSavingShorts(@AuthenticationPrincipal User user){
      List<ShortsDto.shortsResponse> shortsResponseDtoList = shortsService.getAllSavingShorts(user.getId());
      return new ResponseEntity<>(shortsResponseDtoList, HttpStatus.OK);
  }
  @Operation(summary = "소비 교육 쇼츠 조회", description = "소비 교육 쇼츠를 모두 조회합니다.")
  @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
  @PreAuthorize("hasRole('USER')")
  @GetMapping("/edu")
  public ResponseEntity<List<ShortsDto.shortsResponse>> getAllEduShorts(@AuthenticationPrincipal User user){
      List<ShortsDto.shortsResponse> shortsResponseDtoList = shortsService.getAllEduShorts(user.getId());
      return new ResponseEntity<>(shortsResponseDtoList, HttpStatus.OK);
  }
  @Operation(summary = "쇼츠 수정", description = "쇼츠를 수정합니다.")
  @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping(value = "/{shortsId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<HttpStatus> updateShorts(@PathVariable(name = "shortsId") Long shortsId, @ModelAttribute("updateShorts") ShortsDto.updateRequest shortsRequestDto) throws IOException {
      shortsService.updateShorts(shortsId, shortsRequestDto);
      return new ResponseEntity<>(HttpStatus.OK);
  }
  @Operation(summary = "쇼츠 삭제", description = "쇼츠를 삭제합니다.")
  @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping ("/{shortsId}")
  public ResponseEntity<HttpStatus> deleteShorts(@PathVariable(name = "shortsId") Long shortsId){
      shortsService.deleteShorts(shortsId);
      return new ResponseEntity<>(HttpStatus.OK);
  }
}
