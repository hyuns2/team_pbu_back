package projectbuildup.mivv.global.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import projectbuildup.mivv.global.error.exception.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {


    @ExceptionHandler(InterruptedException.class)
    protected ResponseEntity<ErrorResponseDto> handle(InterruptedException e){
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        e.printStackTrace();

        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidationErrorResponseDto> handle(MethodArgumentNotValidException e){
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        e.printStackTrace();

        List<FieldErrorDto> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
           String field = ((FieldError) error ).getField();
           String message = error.getDefaultMessage();
           errors.add(new FieldErrorDto(field, message));
        });
        return new ResponseEntity<>(new ValidationErrorResponseDto(errorCode, errors), errorCode.getStatusCode());
    }

    // Custom Exception

    @ExceptionHandler(CUserNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CUserNotFoundException e){
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CIllegalArgumentException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CIllegalArgumentException e){
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CLoginFailedException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CLoginFailedException e){
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CUserExistException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CUserExistException e){
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CResourceNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CResourceNotFoundException e){
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CRefreshTokenExpiredException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CRefreshTokenExpiredException e){
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CReissueFailedException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CReissueFailedException e){
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CAccessDeniedException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CAccessDeniedException e){
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CInternalServerException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CInternalServerException e){
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CBadRequestException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CBadRequestException e){
        ErrorCode errorCode = e.getErrorCode();
        String message = e.getMessage();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode, message), errorCode.getStatusCode());
    }
    @ExceptionHandler(CWrongPasswordException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CWrongPasswordException e){
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }
    @ExceptionHandler(CVerificationNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CVerificationNotFoundException e){
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CInquiryOverException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CInquiryOverException e){
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CInquiryNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CInquiryNotFoundException e){
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CSavingCountOverException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CSavingCountOverException e){
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }
}
