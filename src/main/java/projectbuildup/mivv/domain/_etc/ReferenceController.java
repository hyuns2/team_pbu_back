package projectbuildup.mivv.domain._etc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import projectbuildup.mivv.domain.challenge.service.RankingService;
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
    private final StringRedisTemplate redisTemplate;

    @Operation(summary = "에러 코드를 조회합니다.", description = "")
    @GetMapping("/ref/error-code")
    @ResponseBody
    public List<ErrorResponseDto> getErrorCode() {
        return Arrays.stream(ErrorCode.values()).map(ErrorResponseDto::new).collect(Collectors.toList());
    }

    @Operation(summary = "랭킹 더미데이터를 주입합니다.", description = "랭킹 조회가 안될 때만 호출하세요")
    @GetMapping("/ref/ranking-data")
    public ResponseEntity<?> injectRankingData() {
        ZSetOperations<String, String> operations = redisTemplate.opsForZSet();
        final String CHALLENGE_1_KEY = "1";
        final String CHALLENGE_2_KEY = "2";
        final String TOTAL_RANKING_KEY = RankingService.TOTAL_RANKING_KEY;
        redisTemplate.delete(CHALLENGE_1_KEY);
        operations.add(CHALLENGE_1_KEY, "9", 9.0001);
        operations.add(CHALLENGE_1_KEY, "8", 8.0001);
        operations.add(CHALLENGE_1_KEY, "7", 7.0001);
        operations.add(CHALLENGE_1_KEY, "6", 6.0001);
        operations.add(CHALLENGE_1_KEY, "5", 5.0001);
        operations.add(CHALLENGE_1_KEY, "4", 4.0001);
        operations.add(CHALLENGE_1_KEY, "3", 3.0001);
        operations.add(CHALLENGE_1_KEY, "2", 2.0001);
        operations.add(CHALLENGE_1_KEY, "1", 6.0003);
        redisTemplate.delete(TOTAL_RANKING_KEY);
        operations.add(TOTAL_RANKING_KEY, "9", 9.0001);
        operations.add(TOTAL_RANKING_KEY, "8", 8.0001);
        operations.add(TOTAL_RANKING_KEY, "7", 7.0001);
        operations.add(TOTAL_RANKING_KEY, "6", 6.0001);
        operations.add(TOTAL_RANKING_KEY, "5", 5.0001);
        operations.add(TOTAL_RANKING_KEY, "4", 4.0001);
        operations.add(TOTAL_RANKING_KEY, "3", 3.0001);
        operations.add(TOTAL_RANKING_KEY, "2", 2.0001);
        operations.add(TOTAL_RANKING_KEY, "1", 16.0004);
        redisTemplate.delete(CHALLENGE_2_KEY);
        operations.add(CHALLENGE_2_KEY, "1", 10.0001);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
