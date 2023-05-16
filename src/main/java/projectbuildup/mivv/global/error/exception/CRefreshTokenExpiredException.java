package projectbuildup.mivv.global.error.exception;

import lombok.Getter;
import projectbuildup.mivv.global.error.ErrorCode;

@Getter
public class CRefreshTokenExpiredException extends RuntimeException{
    private final ErrorCode errorCode;

    public CRefreshTokenExpiredException(){
        super();
        errorCode = ErrorCode.REFRESH_TOKEN_EXPIRED;
    }
}
