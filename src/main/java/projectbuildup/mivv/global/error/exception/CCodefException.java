package projectbuildup.mivv.global.error.exception;

import lombok.Getter;
import projectbuildup.mivv.global.error.ErrorCode;

@Getter
public class CCodefException extends RuntimeException{
    private final String codefCode;
    private final String codefMessage;

    public CCodefException(String codefCode, String codefMessage){
        super();
        this.codefCode = codefCode;
        this.codefMessage = codefMessage;
    }
}
