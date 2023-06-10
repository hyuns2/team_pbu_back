package projectbuildup.mivv.global.error.exception;

import lombok.Getter;
import projectbuildup.mivv.global.error.ErrorCode;

@Getter
public class CAccountExistException extends RuntimeException{
    private final ErrorCode errorCode;

    public CAccountExistException(){
        super();
        errorCode = ErrorCode.ACCOUNT_EXIST;
    }
}
