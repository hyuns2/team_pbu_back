package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;

@Getter
public class CUserNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public CUserNotFoundException(){
        super();
        errorCode = ErrorCode.MEMBER_NOT_FOUND;
    }
}
