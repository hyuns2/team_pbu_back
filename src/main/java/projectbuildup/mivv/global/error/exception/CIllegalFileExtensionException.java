package projectbuildup.mivv.global.error.exception;

import lombok.Getter;
import projectbuildup.mivv.global.error.ErrorCode;

@Getter
public class CIllegalFileExtensionException extends RuntimeException{
    private final ErrorCode errorCode;

    public CIllegalFileExtensionException(){
        super();
        this.errorCode = ErrorCode.ILLEGAL_FILE_EXTENSION_ERROR;
    }
}