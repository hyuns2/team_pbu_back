package projectbuildup.gasomann.global.error.exception;

import lombok.Getter;
import projectbuildup.gasomann.global.error.ErrorCode;

@Getter
public class CInvalidCellException extends RuntimeException{
    ErrorCode errorCode;
    String message;

    public CInvalidCellException(String message){
        super();
        this.errorCode = ErrorCode.INVALID_CELL_ERROR;
        this.message = message;
    }
}
