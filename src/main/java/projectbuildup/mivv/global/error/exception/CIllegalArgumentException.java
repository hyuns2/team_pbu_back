package projectbuildup.mivv.global.error.exception;

import lombok.Getter;
import projectbuildup.mivv.global.error.ErrorCode;

@Getter
public class CIllegalArgumentException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String message;

    public CIllegalArgumentException(String message){
        super();
        this.errorCode = ErrorCode.ILLEGAL_ARGUMENT_ERROR;
        this.message = message;
    }
}
