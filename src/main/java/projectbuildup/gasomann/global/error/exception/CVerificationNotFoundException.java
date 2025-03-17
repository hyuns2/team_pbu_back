package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;

@Getter
public class CVerificationNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public CVerificationNotFoundException(){
        super();
        errorCode = ErrorCode.VERIFICATION_NOT_FOUND;
    }
}
