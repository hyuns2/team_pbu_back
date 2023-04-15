package projectbuildup.mivv.global.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    RESOURCE_NOT_FOUND(-1000, "해당 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    BAD_REQUEST(-1002, "잘못된 접근입니다.", HttpStatus.BAD_REQUEST),
    SAVING_COUNT_OVER(-1003, "절약 가능 횟수를 모두 사용하였습니다.", HttpStatus.BAD_REQUEST),


    // 6000 ~ : 권한, 인증 에러
    LOGIN_FAILED(-6001, "로그인에 실패했습니다.", HttpStatus.UNAUTHORIZED),
    MEMBER_EXIST_ERROR(-6002, "이미 가입된 사용자입니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(-6005, "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    REFRESH_TOKEN_EXPIRED(-6006, "리프레시 토큰이 만료되었습니다. 다시 로그인해주세요.", HttpStatus.FORBIDDEN),
    REISSUE_FAILED(-6007, "토큰 재발급에 실패했습니다.", HttpStatus.BAD_REQUEST),
    MEMBER_NOT_FOUND(-6008, "회원을 조회할 수 없습니다.", HttpStatus.UNAUTHORIZED),
    WRONG_PASSWORD(-6009, "잘못된 비밀번호입니다.", HttpStatus.UNAUTHORIZED),
    VERIFICATION_NOT_FOUND(-6010, "본인인증 정보가 없습니다.", HttpStatus.NOT_FOUND),
    WORTHY_CONSUMPTION_NOT_FOUND(-6011, "가치소비를 조회할 수 없습니다.", HttpStatus.UNAUTHORIZED),

    // 7000 ~ : 시큐리티 에러
    NOT_AUTHORIZED(-7000, "해당 리소스에 접근하기 위한 권한이 없습니다. 시큐리티 권한 인증에 실패했습니다.", HttpStatus.FORBIDDEN),
    AUTHENTICATION_ERROR(-7001, "시큐리티 인증 과정에서 문제가 발생했습니다.", HttpStatus.UNAUTHORIZED),

    JWT_INVALID(-7002, "유효하지 않은 토큰 형식입니다.", HttpStatus.UNAUTHORIZED),
    JWT_EXPIRED(-7003, "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    JWT_BLOCKED(-7004, "사용이 중지된 토큰입니다.", HttpStatus.UNAUTHORIZED),

    //8000 ~ : 클라이언트 에러
    ILLEGAL_ARGUMENT_ERROR(-8000, "잘못된 파라미터입니다.", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR(-8001, "유효성 검증에 실패했습니다.", HttpStatus.BAD_REQUEST),

    // 9000 ~ : 서버 에러
    INTERNAL_SERVER_ERROR(-9999, "서버 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    EMPTY(-11111, "없음", HttpStatus.OK),

    // 15000 ~ : 문의, 아카이빙 관련 에러
    INQUIRY_OVER_ERROR(-15000, "문의 개수 한도를 초과했습니다.", HttpStatus.BAD_REQUEST),
    INQUIRY_NOT_FOUND(-15100, "해당하는 문의를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    CARD_NOT_FOUND(-16000, "해당하는 카드를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);

    final int code;
    final String message;
    final HttpStatus statusCode;

    public static ErrorCode findByName(String name){
        return Arrays.stream(values())
                .filter(type -> type.name().equals(name))
                .findAny()
                .orElse(EMPTY);
    }
}
