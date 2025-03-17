package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;

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
