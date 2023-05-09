package projectbuildup.mivv.domain._etc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import projectbuildup.mivv.global.error.ErrorCode;
import projectbuildup.mivv.global.error.ErrorResponseDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "[0.Reference]", description = "참고용 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReferenceController {

    @Operation(summary="에러 코드를 조회합니다.", description="")
    @PreAuthorize("permitAll()")
    @GetMapping("/ref/error-code")
    @ResponseBody
    public List<ErrorResponseDto> getErrorCode(){
        return Arrays.stream(ErrorCode.values()).map(ErrorResponseDto::new).collect(Collectors.toList());
    }
}