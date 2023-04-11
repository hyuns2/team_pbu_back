package projectbuildup.mivv.global.error.exception;

import lombok.Getter;
import projectbuildup.mivv.global.error.ErrorCode;

@Getter
public class CUserExistException extends RuntimeException{
    private final ErrorCode errorCode;

    public CUserExistException(){
        super();
        errorCode = ErrorCode.MEMBER_EXIST_ERROR;
    }
}
