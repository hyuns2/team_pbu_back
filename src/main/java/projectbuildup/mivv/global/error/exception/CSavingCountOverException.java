package projectbuildup.mivv.global.error.exception;

import lombok.Getter;
import projectbuildup.mivv.global.error.ErrorCode;

@Getter
public class CSavingCountOverException extends RuntimeException{
    private final ErrorCode errorCode;

    public CSavingCountOverException(){
        super();
        errorCode = ErrorCode.SAVING_COUNT_OVER;
    }
}
