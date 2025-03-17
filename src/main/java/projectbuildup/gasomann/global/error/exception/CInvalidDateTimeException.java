package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;

@Getter
public class CInvalidDateTimeException extends RuntimeException{
    ErrorCode errorCode;
    String message;

    public CInvalidDateTimeException(){
        super();
        this.errorCode = ErrorCode.INVALID_DATETIME_ERROR;
    }
}
