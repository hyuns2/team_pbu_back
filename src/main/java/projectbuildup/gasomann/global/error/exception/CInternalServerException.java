package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;

@Getter
public class CInternalServerException extends RuntimeException{
    private final ErrorCode errorCode;

    public CInternalServerException(){
        super();
        errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }
}
