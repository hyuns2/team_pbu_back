package projectbuildup.mivv.global.error.exception;

import lombok.Getter;
import projectbuildup.mivv.global.error.ErrorCode;

@Getter
public class CMemberNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public CMemberNotFoundException(){
        super();
        errorCode = ErrorCode.MEMBER_NOT_FOUND;
    }
}
