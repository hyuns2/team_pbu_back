package projectbuildup.mivv.global.error.exception;

import lombok.Getter;
import projectbuildup.mivv.global.error.ErrorCode;
@Getter
public class CShortsNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public CShortsNotFoundException(){
        super();
        errorCode = ErrorCode.SHORTS_NOT_FOUND;
    }
}
