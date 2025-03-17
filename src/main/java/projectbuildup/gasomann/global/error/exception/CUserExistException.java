package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;

@Getter
public class CUserExistException extends RuntimeException{
    private final ErrorCode errorCode;

    public CUserExistException(){
        super();
        errorCode = ErrorCode.MEMBER_EXIST_ERROR;
    }
}
