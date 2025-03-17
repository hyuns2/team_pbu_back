package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;

@Getter
public class CNotOwnAccountException extends RuntimeException{
    private final ErrorCode errorCode;

    public CNotOwnAccountException(){
        super();
        errorCode = ErrorCode.NOT_OWN_ACCOUNT;
    }

}
