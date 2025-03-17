package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;

@Getter
public class CAccountNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;
    public CAccountNotFoundException(){
        super();
        errorCode = ErrorCode.ACCOUNT_NOT_FOUND;
    }

}
