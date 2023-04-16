package projectbuildup.mivv.global.error.exception;

import lombok.Data;
import projectbuildup.mivv.global.error.ErrorCode;

@Data
public class CCardNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public CCardNotFoundException(){
        super();
        errorCode = ErrorCode.CARD_NOT_FOUND;
    }

}
