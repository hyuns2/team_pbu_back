package projectbuildup.mivv.global.error.exception;

import lombok.Data;
import projectbuildup.mivv.global.error.ErrorCode;

@Data
public class CInquiryNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public CInquiryNotFoundException(){
        super();
        errorCode = ErrorCode.INQUIRY_NOT_FOUND;
    }

}
