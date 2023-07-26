package com.berno.kakaopaysecclone.common.exception;


import com.berno.kakaopaysecclone.common.enums.ExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = {CustomException.class})
    protected ErrorResponse handleCustomException(CustomException e) {
        ExceptionType exceptionType = e.getExceptionType();
        log.error("Exception Throw : {}", exceptionType);
        return ErrorResponse.create(e, e.getExceptionType().getStatus(), e.getMessage());
    }
}
