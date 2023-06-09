package projectbuildup.mivv.global.error.exception;

import lombok.Data;
import projectbuildup.mivv.global.error.ErrorCode;

@Data
public class CCardTypeNotMatchException extends RuntimeException {

    private final ErrorCode errorCode;

    public CCardTypeNotMatchException(){
        super();
        errorCode = ErrorCode.CARD_TYPE_NOT_MATCH;
    }

}