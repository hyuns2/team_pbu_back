package projectbuildup.mivv.global.error.exception;

import lombok.Getter;
import projectbuildup.mivv.global.error.ErrorCode;

@Getter
public class CNotOwnAccountException extends RuntimeException{
    private final ErrorCode errorCode;

    public CNotOwnAccountException(){
        super();
        errorCode = ErrorCode.NOT_OWN_ACCOUNT;
    }

}
