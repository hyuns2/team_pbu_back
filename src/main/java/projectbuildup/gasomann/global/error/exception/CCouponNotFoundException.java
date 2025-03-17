package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;

@Getter
public class CCouponNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;
    public CCouponNotFoundException(){
        super();
        errorCode = ErrorCode.COUPON_NOT_FOUND;
    }

}
