package projectbuildup.mivv.global.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
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

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ValidationErrorResponseDto> handle(BindException e) {
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        e.printStackTrace();

        List<FieldErrorDto> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.add(new FieldErrorDto(field, message));
        });
        log.error("ExceptionAdvice - BindException 예외 발생");
        return new ResponseEntity<>(new ValidationErrorResponseDto(errorCode, errors), errorCode.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponseDto> handle(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        e.printStackTrace();

        List<FieldErrorDto> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.add(new FieldErrorDto(field, message));
        });
        log.error("ExceptionAdvice - MethodArgumentNotValidException 예외 발생");
        return new ResponseEntity<>(new ValidationErrorResponseDto(errorCode, errors), errorCode.getStatusCode());
    }

    @ExceptionHandler(InterruptedException.class)
    protected ResponseEntity<ErrorResponseDto> handle(InterruptedException e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        e.printStackTrace();

        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    // Custom Exception

    @ExceptionHandler(CUserNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CUserNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CIllegalArgumentException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CIllegalArgumentException e) {
        ErrorCode errorCode = e.getErrorCode();
        String message = e.getMessage();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode, message), errorCode.getStatusCode());
    }

    @ExceptionHandler(CLoginFailedException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CLoginFailedException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CUserExistException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CUserExistException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CResourceNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CResourceNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CRefreshTokenExpiredException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CRefreshTokenExpiredException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CReissueFailedException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CReissueFailedException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CAccessDeniedException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CAccessDeniedException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CInternalServerException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CInternalServerException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CBadRequestException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CBadRequestException e) {
        ErrorCode errorCode = e.getErrorCode();
        String message = e.getMessage();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode, message), errorCode.getStatusCode());
    }

    @ExceptionHandler(CWrongPasswordException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CWrongPasswordException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CVerificationNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CVerificationNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CInquiryOverException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CInquiryOverException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CInquiryNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CInquiryNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CInquiryNotMatchException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CInquiryNotMatchException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CSavingCountOverException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CSavingCountOverException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CCardNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CCardNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CCardTypeNotMatchException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CCardTypeNotMatchException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CFileNotInputException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CFileNotInputException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CIllegalFileExtensionException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CIllegalFileExtensionException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CInvalidCellException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CInvalidCellException e) {
        ErrorCode errorCode = e.getErrorCode();
        String message = e.getMessage();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode, message), errorCode.getStatusCode());
    }

    @ExceptionHandler(CUnknownIpException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CUnknownIpException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CInvalidCardConditionException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CInvalidCardConditionException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CNotificationNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CNotificationNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CAccountNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CAccountNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    @ExceptionHandler(CCodefException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CCodefException e) {
        String code = e.getCodefCode();
        String message = e.getCodefMessage();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(code, message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CNotOwnAccountException.class)
    protected ResponseEntity<ErrorResponseDto> handle(CNotOwnAccountException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }
}