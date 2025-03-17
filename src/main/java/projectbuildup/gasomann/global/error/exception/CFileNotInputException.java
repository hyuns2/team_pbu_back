package projectbuildup.gasomann.global.error.exception;

import lombok.Data;
import projectbuildup.gasomann.global.error.ErrorCode;

@Data
public class CFileNotInputException extends RuntimeException {

    private final ErrorCode errorCode;

    public CFileNotInputException(){
        super();
        errorCode = ErrorCode.FILE_NOT_INPUT_ERROR;
    }

}
