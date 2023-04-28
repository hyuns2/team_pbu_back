package projectbuildup.mivv.global.error.exception;

import lombok.Getter;
import projectbuildup.mivv.global.error.ErrorCode;

@Getter
public class CWorthyConsumptionNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public CWorthyConsumptionNotFoundException(){
        super();
        errorCode = ErrorCode.WORTHY_CONSUMPTION_NOT_FOUND;
    }

}
