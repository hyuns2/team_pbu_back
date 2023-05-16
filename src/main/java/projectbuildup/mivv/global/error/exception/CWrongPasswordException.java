package projectbuildup.mivv.global.error.exception;

import lombok.Getter;
import projectbuildup.mivv.global.error.ErrorCode;

@Getter
public class CWrongPasswordException extends RuntimeException{
    private final ErrorCode errorCode;

    public CWrongPasswordException(){
        super();
        errorCode = ErrorCode.WRONG_PASSWORD;
    }
}
