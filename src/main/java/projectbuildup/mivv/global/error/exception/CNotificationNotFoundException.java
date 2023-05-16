package projectbuildup.mivv.global.error.exception;

import lombok.Getter;
import projectbuildup.mivv.global.error.ErrorCode;

@Getter
public class CNotificationNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public CNotificationNotFoundException(){
        super();
        errorCode = ErrorCode.NOTIFICATION_NOT_FOUND;
    }
}
