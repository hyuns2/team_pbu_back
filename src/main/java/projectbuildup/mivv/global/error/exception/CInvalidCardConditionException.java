package projectbuildup.mivv.global.error.exception;

import lombok.Getter;
import projectbuildup.mivv.global.error.ErrorCode;

@Getter
public class CInvalidCardConditionException extends RuntimeException{
    ErrorCode errorCode;
    String message;

    public CInvalidCardConditionException(){
        super();
        this.errorCode = ErrorCode.INVALID_CARD_CONDITION;
    }
}
