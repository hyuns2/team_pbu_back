package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;

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
