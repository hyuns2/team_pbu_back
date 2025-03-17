package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;

@Getter
public class CWorthyConsumptionNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public CWorthyConsumptionNotFoundException(){
        super();
        errorCode = ErrorCode.WORTHY_CONSUMPTION_NOT_FOUND;
    }

}
