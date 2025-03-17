package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;

@Getter
public class CWrongPasswordException extends RuntimeException{
    private final ErrorCode errorCode;

    public CWrongPasswordException(){
        super();
        errorCode = ErrorCode.WRONG_PASSWORD;
    }
}
