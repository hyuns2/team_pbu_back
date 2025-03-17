package projectbuildup.gasomann.global.error.exception;

import lombok.Data;
import projectbuildup.gasomann.global.error.ErrorCode;

@Data
public class CInquiryNotMatchException extends RuntimeException {

    private final ErrorCode errorCode;

    public CInquiryNotMatchException(){
        super();
        errorCode = ErrorCode.INQUIRY_NOT_MATCH;
    }

}
