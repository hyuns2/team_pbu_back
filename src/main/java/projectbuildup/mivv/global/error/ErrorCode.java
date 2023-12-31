package projectbuildup.mivv.global.error;

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
    ACCOUNT_NOT_FOUND(-1004, "해당 계좌를 조회할 수 없습니다.", HttpStatus.NOT_FOUND),
    ACCOUNT_EXIST(-1005, "연결된 계좌가 이미 존재합니다.", HttpStatus.BAD_REQUEST),

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
    NOT_OWN_ACCOUNT(-6012, "사용자 명의의 계좌가 아닙니다.", HttpStatus.UNAUTHORIZED),

    // 7000 ~ : 시큐리티 에러
    NOT_AUTHORIZED(-7000, "해당 리소스에 접근하기 위한 권한이 없습니다. 시큐리티 권한 인증에 실패했습니다.", HttpStatus.FORBIDDEN),
    AUTHENTICATION_ERROR(-7001, "시큐리티 인증 과정에서 문제가 발생했습니다.", HttpStatus.UNAUTHORIZED),

    JWT_INVALID(-7002, "유효하지 않은 토큰 형식입니다.", HttpStatus.UNAUTHORIZED),
    JWT_EXPIRED(-7003, "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    JWT_BLOCKED(-7004, "사용이 중지된 토큰입니다.", HttpStatus.UNAUTHORIZED),

    //8000 ~ : 클라이언트 에러
    ILLEGAL_ARGUMENT_ERROR(-8000, "잘못된 파라미터입니다.", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR(-8001, "유효성 검증에 실패했습니다.", HttpStatus.BAD_REQUEST),
    FILE_NOT_INPUT_ERROR(8002, "파일 수신에 실패했습니다.", HttpStatus.BAD_REQUEST),
    ILLEGAL_FILE_EXTENSION_ERROR(-8003, "해당 파일 확장자는 지원하지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_CELL_ERROR(8004, "엑셀 파일의 데이터가 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_DATETIME_ERROR(8005, "유효한 날짜 또는 시간이 아닙니다.", HttpStatus.BAD_REQUEST),

    // 9000 ~ : 서버 에러
    INTERNAL_SERVER_ERROR(-9999, "서버 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    EMPTY(-11111, "없음", HttpStatus.OK),
    UNKNOWN_IP_ERROR(11112, "서버 Ip를 찾을 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 15000 ~ : 문의, 아카이빙, 알림 관련 에러
    INQUIRY_OVER_ERROR(-15000, "문의 개수 한도를 초과했습니다.", HttpStatus.BAD_REQUEST),
    INQUIRY_NOT_FOUND(-15100, "해당하는 문의를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    INQUIRY_NOT_MATCH(-15200, "해당 문의는 현재 유저의 문의가 아닙니다.", HttpStatus.BAD_REQUEST),
    CARD_NOT_FOUND(-16000, "해당하는 카드를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    CARD_TYPE_NOT_MATCH(-15200, "입력한 카드 ID의 타입을 확인해주세요.", HttpStatus.BAD_REQUEST),
    INVALID_CARD_CONDITION(-16100, "카드 조건은 하나 이상이여야 합니다.", HttpStatus.BAD_REQUEST),
    NOTIFICATION_NOT_FOUND(-17000, "해당하는 알림을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),


    // 20000 ~ : 가치소비 관련 에러
    COUPON_NOT_FOUND(-20000, "쿠폰을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),


    // 25000 ~ : 쇼츠 관련 에러
    SHORTS_NOT_FOUND(-25000, "숏츠를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);


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