package projectbuildup.mivv.global.error.exception;

import lombok.Getter;
import projectbuildup.mivv.global.error.ErrorCode;

@Getter
public class CBadRequestException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String message;

    public CBadRequestException(String message){
        super();
        this.errorCode = ErrorCode.BAD_REQUEST;
        this.message = message;
    }
}
