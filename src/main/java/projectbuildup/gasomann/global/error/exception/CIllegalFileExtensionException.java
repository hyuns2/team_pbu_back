package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;

@Getter
public class CIllegalFileExtensionException extends RuntimeException{
    private final ErrorCode errorCode;

    public CIllegalFileExtensionException(){
        super();
        this.errorCode = ErrorCode.ILLEGAL_FILE_EXTENSION_ERROR;
    }
}