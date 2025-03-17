package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;
@Getter
public class CShortsNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public CShortsNotFoundException(){
        super();
        errorCode = ErrorCode.SHORTS_NOT_FOUND;
    }
}
