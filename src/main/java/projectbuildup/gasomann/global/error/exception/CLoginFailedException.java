package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;

@Getter
public class CLoginFailedException extends RuntimeException{
    private final ErrorCode errorCode;

    public CLoginFailedException(){
        super();
        errorCode = ErrorCode.LOGIN_FAILED;
    }
}
