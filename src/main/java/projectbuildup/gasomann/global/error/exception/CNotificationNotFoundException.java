package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;

@Getter
public class CNotificationNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public CNotificationNotFoundException(){
        super();
        errorCode = ErrorCode.NOTIFICATION_NOT_FOUND;
    }
}
