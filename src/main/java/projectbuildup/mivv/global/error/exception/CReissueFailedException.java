package projectbuildup.mivv.global.error.exception;

import lombok.Getter;
import projectbuildup.mivv.global.error.ErrorCode;

@Getter
public class CReissueFailedException extends RuntimeException{
    private final ErrorCode errorCode;

    public CReissueFailedException(){
        super();
        errorCode = ErrorCode.REISSUE_FAILED;
    }
}
