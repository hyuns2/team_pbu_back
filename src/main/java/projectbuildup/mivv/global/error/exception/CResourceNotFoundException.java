package projectbuildup.mivv.global.error.exception;

import lombok.Getter;
import projectbuildup.mivv.global.error.ErrorCode;

@Getter
public class CResourceNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public CResourceNotFoundException(){
        super();
        errorCode = ErrorCode.RESOURCE_NOT_FOUND;
    }
}
