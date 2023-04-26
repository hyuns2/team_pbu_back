package projectbuildup.mivv.global.error.exception;

import lombok.Getter;
import projectbuildup.mivv.global.error.ErrorCode;

@Getter
public class CUserNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public CUserNotFoundException(){
        super();
        errorCode = ErrorCode.MEMBER_NOT_FOUND;
    }
}
