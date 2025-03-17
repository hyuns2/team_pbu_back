package projectbuildup.gasomann.global.error.exception;

import lombok.Data;
import projectbuildup.gasomann.global.error.ErrorCode;

@Data
public class CInquiryOverException extends RuntimeException {

    private final ErrorCode errorCode;

    public CInquiryOverException(){
        super();
        errorCode = ErrorCode.INQUIRY_OVER_ERROR;
    }

}
