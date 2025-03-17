package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;

@Getter
public class CUnknownIpException extends RuntimeException{
    private final ErrorCode errorCode;

    public CUnknownIpException(){
        super();
        errorCode = ErrorCode.UNKNOWN_IP_ERROR;
    }
}
