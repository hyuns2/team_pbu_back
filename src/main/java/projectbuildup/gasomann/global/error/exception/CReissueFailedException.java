package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;

@Getter
public class CReissueFailedException extends RuntimeException{
    private final ErrorCode errorCode;

    public CReissueFailedException(){
        super();
        errorCode = ErrorCode.REISSUE_FAILED;
    }
}
