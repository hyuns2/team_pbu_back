package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;

@Getter
public class CAccessDeniedException extends RuntimeException{
    ErrorCode errorCode;
    String message;

    public CAccessDeniedException(String message){
        super();
        this.errorCode = ErrorCode.ACCESS_DENIED;
        this.message = message;
    }
}
